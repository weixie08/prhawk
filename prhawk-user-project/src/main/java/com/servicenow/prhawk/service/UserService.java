package com.servicenow.prhawk.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicenow.prhawk.core.LastPageLink;
import com.servicenow.prhawk.model.*;
@Service("userService")
public class UserService {
	@Autowired
	private Environment env;
	public List<UserRepoDetail> getUserDetail(String userName) {
		List<UserRepoDetail> list = getUserReposDetail(userName);
		return list;
	}
	
	private List<UserRepoDetail> getUserReposDetail(String userName){
		List<UserRepoDetail> userRepoDetails = new ArrayList<UserRepoDetail>();
		//default pull item per page in github
		int userItemNumPerPage = 30;
		String userRepoTemplate = env.getProperty("github.fetchUserReposUrl");
		String fetchUserRepoUrl = String.format(userRepoTemplate, userName);
		String itemPerPagePrefix = env.getProperty("github.itemPerPagePrefix");
		String userItemPerPageNum = env.getProperty("github.userPerPageNum");
		
		try {
			userItemNumPerPage = Integer.parseInt(userItemPerPageNum);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		boolean fetchData = true;
		ObjectMapper mapper = new ObjectMapper();
        
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        int currentPage = 1;
        int retry = 0;
		//fetch the default user data from https://api.github.com/users/%s/repos
		while(fetchData) {
			try {
				String userRepoUrl = fetchUserRepoUrl + "?page=" + currentPage + "&" + itemPerPagePrefix + userItemNumPerPage;
				String response = getHttpResponse(userRepoUrl);
		        
		        List<UserRepoDetail> newUserRepos = mapper.readValue(response.toString(), new TypeReference<List<UserRepoDetail>>(){});
		        userRepoDetails.addAll(newUserRepos);
		        if(newUserRepos.size() < userItemNumPerPage) {
		        	fetchData = false;
		        } else {
		        	currentPage++;
		        }		        
			} catch(Exception e) {	
				retry++;
				e.printStackTrace();
				//if out of limitation, stop here once retry more than 3 times
				if(retry >= 3) {
					return userRepoDetails;
				}
			}      
		}
		//fetch the repo pull number for each repo
		String fetchRepoPullTemp = env.getProperty("github.fetchRepoPullDetail");
		
		for(UserRepoDetail item: userRepoDetails) {
			String userRepoPullPrefix = String.format(fetchRepoPullTemp, userName, item.getRepoName());
			item.setPullTimes(getRepoPullTimes(userRepoPullPrefix));;
		}
		userRepoDetails.sort((s1, s2) -> s2.getPullTimes() - s1.getPullTimes());
        return userRepoDetails;
	}
	
	//this function is use to send th fetch pulls request for setup the per_page to 1,
	//in this case, if have the link header, then the last page number will be the total page number
	//of the repo, otherwise, will need to pass the return string and check if the pull request is
	//one or zero.
	public int getRepoPullTimes(String url) {
		int lastPage = 1;
		int pullTimes = 0;
		//only set the fetch item to be one, so the last page number returned 
		//is the item number need to return
		url = url + "?" + env.getProperty("github.itemPerPagePrefix") + 1;
		try {
			URL urlItem = new URL(url);
	        HttpURLConnection conn = (HttpURLConnection) urlItem.openConnection();
	        setupConnection(conn);
	        String linkHeader = conn.getHeaderField("Link");
	        if(linkHeader != null) {
	        	LastPageLink pagelink = new LastPageLink(linkHeader);
	        	lastPage = pagelink.getPage();
	        	pullTimes = lastPage;
	        } else {
	        	//should check whether it is only one pull or no pull
	        	BufferedReader bufferReader = null;
	        	try {
	        		bufferReader = new BufferedReader(
		    	            new InputStreamReader(conn.getInputStream()));
		    	        String inputLine;
		    	        StringBuffer response = new StringBuffer();
		    	        while ((inputLine = bufferReader .readLine()) != null) {
		    	            response.append(inputLine);
		    	        } 
		    	        
		    	        ObjectMapper mapper = new ObjectMapper();
		    	        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    	        List<RepoPullDetail> lastPagePulls = mapper.readValue(response.toString(), new TypeReference<List<RepoPullDetail>>(){});
		    	        pullTimes = lastPagePulls.size();
	        	} catch(Exception e) {
	    			e.printStackTrace();
	    		} finally {
	    			try {
	    				if(bufferReader != null) {
	    					bufferReader.close();
	    				}
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		}
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pullTimes;
	}
	
	private String getHttpResponse(String url) {
		BufferedReader bufferReader = null;
		String result = "";
		try {
			URL obj = new URL(url);
	        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
	        setupConnection(conn);
	        
	        bufferReader = new BufferedReader(
	            new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = bufferReader .readLine()) != null) {
	            response.append(inputLine);
	        } 
	        result = response.toString();	        
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bufferReader != null) {
					bufferReader.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private void setupConnection(HttpURLConnection conn) {
		try {
			String authentication = env.getProperty("github.authentication");
			String basicAuth = "Basic " + authentication;
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

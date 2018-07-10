package com.servicenow.prhawk.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRepoDetail {
	@JsonProperty("full_name")
	private String fullName;
	@JsonProperty("name")
	private String repoName;
	@JsonProperty("html_url")
	private String url;
	
	private int pullTimes;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPullTimes() {
		return pullTimes;
	}
	public void setPullTimes(int pullTimes) {
		this.pullTimes = pullTimes;
	}
	
	public UserRepoDetail() {};
	public UserRepoDetail(String fullName, int pullTimes, String repoName, String url) {
		this.fullName = fullName;
		this.repoName = repoName;
		this.url = url;
		this.pullTimes = pullTimes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
	        
	    if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	    	return false;
	    }
	    UserRepoDetail userRepoDetail = (UserRepoDetail) obj;
	    
	    return Objects.equals(repoName, userRepoDetail.getRepoName())
	            && Objects.equals(fullName, userRepoDetail.getFullName())
	            && Objects.equals(url, userRepoDetail.getUrl())
	            && Objects.equals(pullTimes, userRepoDetail.getPullTimes());		
	}
}

package com.servicenow.prhawk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImp {
	  @Autowired
	  private Environment env;
	public String getUserDetail(String userName) {
		String userInfo = env.getProperty("github.authentication");
		
		return userInfo;
	}
}

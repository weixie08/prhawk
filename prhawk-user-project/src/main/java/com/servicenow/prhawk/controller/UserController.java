package com.servicenow.prhawk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.servicenow.prhawk.service.UserServiceImp;

@Controller
public class UserController {
  
  // The Environment object will be used to read parameters from the 
  // application.properties configuration file
  @Autowired
  private UserServiceImp userService;
  
  /**
   * Show the index page containing the form for uploading a file.
   */
  @RequestMapping("/")
  public String index() {
    return "index.html";
  }
  
  @RequestMapping(value = "/user/{userName}", method = RequestMethod.GET)
  @ResponseBody
  public Object getEmployee(String userName) {
	return userService.getUserDetail(userName);
  }
} 


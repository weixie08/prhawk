package com.servicenow.prhawk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.servicenow.prhawk.service.UserService;

@Controller
public class UserController {
  
  // The Environment object will be used to read parameters from the 
  // application.properties configuration file
  @Autowired
  private UserService userService;
  
  @RequestMapping("/user/{userName}")
  public String handler (Model model, @PathVariable String userName) {
      model.addAttribute("items",
    		  userService.getUserDetail(userName));
     return "githubUserInfo";
  }
  
  @RequestMapping("/}")
  public String home(){
      return "index";
  }
} 


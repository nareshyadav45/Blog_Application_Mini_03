package com.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class LoggedInUserDetailsUtil {
	
  @Autowired	
  private HttpSession httpSession;	
	
  public  Integer loggedInUserId() {
	  Object attribute = httpSession.getAttribute("LoggedInUserId");
		String string = attribute.toString();
		int int1 = Integer.parseInt(string);
		Integer userId = Integer.valueOf(int1);
	return userId;
	  
  }
	
	
}

package com.blog.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.binding.LoginForm;
import com.blog.binding.RegistrationForm;
import com.blog.service.UserDetailsService;

@RestController
public class UserRestController {

	@Autowired
	private UserDetailsService service;
	
	@PostMapping("/registration")
	public String registration(@RequestBody RegistrationForm form) {
		String userRegistration = this.service.userRegistration(form);
		return userRegistration;
	}
	
	@GetMapping("/login")
	public Object login(@RequestBody LoginForm loginForm) {
		Object userLogin = this.service.userLogin(loginForm);
		return userLogin;
	}
	
	
}

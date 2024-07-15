package com.blog.service;

import com.blog.binding.LoginForm;
import com.blog.binding.RegistrationForm;

public interface UserDetailsService {
	
	public String userRegistration(RegistrationForm registrationForm);
	
	public Object userLogin(LoginForm loginForm);

}

package com.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.binding.LoginForm;
import com.blog.binding.RegistrationForm;
import com.blog.entity.UserDetailsEntity;
import com.blog.repository.UserDetailsRepository;
import com.blog.service.BlogDetailsService;
import com.blog.service.UserDetailsService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserDeatailsSereviceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsRepository detailsRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private BlogDetailsService blogDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public String userRegistration(RegistrationForm registrationForm) {

		UserDetailsEntity entity = this.detailsRepository.findByEmail(registrationForm.getEmail());
		if (entity != null) {
			return "Invalid Mail Id , Mail Already Exist";
		}

		if ((registrationForm.getPassword() == null || registrationForm.getPassword().isEmpty())
				&& (registrationForm.getFirstName() == null || registrationForm.getFirstName().isEmpty())
				&& (registrationForm.getLastName() == null || registrationForm.getLastName().isEmpty())
				&& (registrationForm.getEmail() == null || registrationForm.getEmail().isEmpty())) {
			return "please fill the all fields";
		} else if (registrationForm.getPassword() == null || registrationForm.getPassword().isEmpty()) {
			return "password is null or empty ,please enter password";
		} else if (registrationForm.getFirstName().equals("") || registrationForm.getFirstName() == null) {
			return "firstname field is null,please enter firstname";
		} else if (registrationForm.getLastName().equals("") || registrationForm.getLastName() == null) {
			return "LastName field is empty,Please Enter LastName";
		} else if (registrationForm.getEmail().equals("") || registrationForm.getEmail() == null) {
			return "Email is empty";

		}

		UserDetailsEntity detailsEntity = new UserDetailsEntity();
		
		String encodePassowrd = this.bCryptPasswordEncoder.encode(registrationForm.getPassword());
		
		registrationForm.setPassword(encodePassowrd);
        
		BeanUtils.copyProperties(registrationForm, detailsEntity);

		this.detailsRepository.save(detailsEntity);

		return "Successfully Registered";

	}

	@Override
	public Object userLogin(LoginForm loginForm) {
		Object object=null;
		
		// TODO : Check If entered mail is correct or not
		if(loginForm.getEmail()==null  || loginForm.getEmail().isEmpty()) {
			return object="please enter and email";
		}
		
		if(loginForm.getPassword()==null||loginForm.getPassword().isEmpty())
		{
			return object="please ennter password";
		}		
		
		UserDetailsEntity byEmail = this.detailsRepository.findByEmail(loginForm.getEmail());
		if(byEmail==null) {
			return object="Given Mail id is invalid";
		}
		 
		if(byEmail.getEmail().equals(loginForm.getEmail())){
			
			boolean matches = this.bCryptPasswordEncoder.matches(loginForm.getPassword(), byEmail.getPassword());
			if(matches==true) {
			//if(byEmail.getPassword().equals(loginForm.getPassword())) {
				
				//TODO: Call Blog controller method fetch-List-Blogs of Logged in User
				
				httpSession.setAttribute("LoggedInUserId", byEmail.getUserId());
				
				Object attribute = httpSession.getAttribute("LoggedInUserId");
				String string = attribute.toString();
				int User = Integer.parseInt(string);
				Integer UserId = Integer.valueOf(User);
				
				//TODO: Call BlogService GetListOfBlogs Of Logged In user
				return blogDetailsService.fetchBlogsLoggedUser();
			//}
			}else {
				return object="Invalid password";
			}
		}
		return object;
		
	}

}

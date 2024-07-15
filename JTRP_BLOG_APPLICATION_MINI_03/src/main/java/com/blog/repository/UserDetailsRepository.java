package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer>{
	
	public UserDetailsEntity findByEmail(String email);

}

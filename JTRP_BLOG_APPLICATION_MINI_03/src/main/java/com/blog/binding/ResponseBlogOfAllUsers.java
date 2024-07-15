package com.blog.binding;

import java.time.LocalDate;

public class ResponseBlogOfAllUsers {
	
	private String blogTitle;
	private LocalDate createdDate;
	private String blogShortDescription;
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public String getBlogShortDescription() {
		return blogShortDescription;
	}
	public void setBlogShortDescription(String blogShortDescription) {
		this.blogShortDescription = blogShortDescription;
	}
	
	
}

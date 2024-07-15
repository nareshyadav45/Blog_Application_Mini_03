package com.blog.binding;

import java.time.LocalDate;

public class BlogResponse {

	private Integer blogId;
	private String blogTitle;
	private String blogShortDes;
	private LocalDate blogCreatedDate;
	public Integer getBlogId() {
		return blogId;
	}
	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getBlogShortDes() {
		return blogShortDes;
	}
	public void setBlogShortDes(String blogShortDes) {
		this.blogShortDes = blogShortDes;
	}
	public LocalDate getBlogCreatedDate() {
		return blogCreatedDate;
	}
	public void setBlogCreatedDate(LocalDate blogCreatedDate) {
		this.blogCreatedDate = blogCreatedDate;
	}
	public BlogResponse(Integer blogId, String blogTitle, String blogShortDes, LocalDate blogCreatedDate) {
		super();
		this.blogId = blogId;
		this.blogTitle = blogTitle;
		this.blogShortDes = blogShortDes;
		this.blogCreatedDate = blogCreatedDate;
	}
	public BlogResponse() {
		
	}
	
	
	
}

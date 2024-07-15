package com.blog.binding;

import java.time.LocalDate;

public class BlogResponseBean {

	private Integer blogId;
	private String blogTitle;
	private String blogShortDes;
	private LocalDate createdDate;
	
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
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public BlogResponseBean(Integer blogId, String blogTitle, String blogShortDes, LocalDate createdDate) {
		super();
		this.blogId = blogId;
		this.blogTitle = blogTitle;
		this.blogShortDes = blogShortDes;
		this.createdDate = createdDate;
	}
	
	
}

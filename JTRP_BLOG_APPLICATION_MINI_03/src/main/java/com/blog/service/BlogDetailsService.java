package com.blog.service;

import com.blog.binding.BlogForm;

public interface BlogDetailsService {
	
	public Boolean addBlog(BlogForm blogForm);
	
	public Object fetchBlogsLoggedUser();
	
	public Object fetchBlogsLoggedUserWithSearchFun(String search);
	
	public Object getBlogsOfUserLoggedIn();
	
	public Object updateBlog(Integer blogId,BlogForm blogForm);
	
	public Boolean deleteBlogByBlogId(Integer blogId);
	
	public Object fetchBlogsOfUsers();
	
	public Object fetchCommentsOfBlogId(Integer blogId);
	
	public Object fetchBlogsBySearchFunctionality(String blogSearch);
	
	

}

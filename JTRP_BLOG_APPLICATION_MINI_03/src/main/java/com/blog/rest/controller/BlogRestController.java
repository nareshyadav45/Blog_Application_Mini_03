package com.blog.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.binding.BlogForm;
import com.blog.service.BlogDetailsService;

@RestController
public class BlogRestController {
	
	@Autowired
	private BlogDetailsService blogDetailsService;
	
	@PostMapping("/AddBlog")
	public String addBlog(@RequestBody BlogForm blogForm) {
		 Boolean blog = blogDetailsService.addBlog(blogForm);
		 if(blog==false) {
			 return "User Not Logged In";
		 }
		 else {
			 return "Successfully Blog Added";
		 }
	}
	
	@GetMapping("/fetchBlogsOfLoggedInUser")
	public Object fetchListBlogById() {
		Object fetchBlogsLoggedUser = this.blogDetailsService.fetchBlogsLoggedUser();
		return fetchBlogsLoggedUser;
		
	}
	
	@GetMapping("/getBlogs")
	public Object getBlogsOfUserOfLoggedInUser() {
		
		Object blogsOfUserLoggedIn = this.blogDetailsService.getBlogsOfUserLoggedIn();
		return blogsOfUserLoggedIn;
		
	}
	
	@PatchMapping("/update")
	public Object updateBlogById(@RequestParam("blogId") Integer blogId,@RequestBody BlogForm blogForm) {
		
		Object updateBlog = this.blogDetailsService.updateBlog(blogId, blogForm);
		
		return updateBlog;
	}
	
	@GetMapping("/blogs")
	public Object listOfBlogsOfAllUsers() {
		Object fetchBlogsOfUsers = this.blogDetailsService.fetchBlogsOfUsers();
		if(fetchBlogsOfUsers==null) {
			return "No Blogs Found";
		}
		return fetchBlogsOfUsers;
	}
	
	@GetMapping("/comments")
	public Object commentsOfBlog(@RequestParam("blogId") Integer blogId) {
		
		Object fetchCommentsOfBlogId = this.blogDetailsService.fetchCommentsOfBlogId(blogId);
		return fetchCommentsOfBlogId;
		
	}
	
	@PatchMapping("/softDeleteOfBlog")
	public String softDeleteOFBlogByBlogId(@RequestParam("blogId") Integer blogId) {
		
		Boolean deleteBlogByBlogId = this.blogDetailsService.deleteBlogByBlogId(blogId);
		if(deleteBlogByBlogId==true) {
			return "Blog Deleted Successfully";
			
		}else {
			return "Blog Not Found With Given BlogId";
		}
		
		
	}
	
	@GetMapping("/fetchBlogsSearch")
	public Object fetchBlogsBySearchKeyWord(@RequestParam("key") String searchWord) {
		Object fetchBlogsBySearchFunctionality = this.blogDetailsService.fetchBlogsBySearchFunctionality(searchWord);
		return fetchBlogsBySearchFunctionality;
	}
	
	@GetMapping("/fetchBlogsSearchCriteria")
	public Object findListOfBlogsOfUserBySearchCriteria(@RequestParam("search") String search) {
		Object fetchBlogsLoggedUserWithSearchFun = this.blogDetailsService.fetchBlogsLoggedUserWithSearchFun(search);
		return fetchBlogsLoggedUserWithSearchFun;
		
	}
	
	
	
	
	
	
	
}

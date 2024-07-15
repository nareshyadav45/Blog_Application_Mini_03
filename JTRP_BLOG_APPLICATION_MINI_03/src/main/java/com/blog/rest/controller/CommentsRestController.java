package com.blog.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.binding.CommentForm;
import com.blog.service.CommentsSevice;

@RestController
public class CommentsRestController {

	@Autowired
	private CommentsSevice commentsSevice;
	
	@PostMapping("/addComment")
	public String addComment(@RequestParam("blogId") Integer blogId,@RequestBody  CommentForm commentForm) {
		
		String comment = commentsSevice.addComment(blogId, commentForm);
	
		return comment;
	}
	
	@GetMapping("/CommentsUser")
	public Object blogsOfLoggedInUser() {
		Object fetchListOfCommentsOfLoggedInUser = this.commentsSevice.fetchListOfCommentsOfLoggedInUser();
		return fetchListOfCommentsOfLoggedInUser;
	}
	

	@PatchMapping("/commentDelete")
	public String deleteComment(@RequestParam("comment") Integer commentId) {
		
		Boolean softedeleteOfCommentByCmtID = this.commentsSevice.softedeleteOfCommentByCmtID(commentId);
		if(softedeleteOfCommentByCmtID==true) {
			return "Successfully Comment Deleted";
			
		}else {
			return "Comment Not Found";
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}

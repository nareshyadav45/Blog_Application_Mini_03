package com.blog.service;

import com.blog.binding.CommentForm;

public interface CommentsSevice {

	public String addComment(Integer blogId,CommentForm commentForm);
	
	public Object fetchListOfCommentsOfLoggedInUser();
	
	public Boolean softedeleteOfCommentByCmtID(Integer commentId); 
	
	
}

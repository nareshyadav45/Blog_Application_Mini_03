package com.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.binding.CommentForm;
import com.blog.binding.CommetsUserResponseBean;
import com.blog.binding.CommetsUserResponseBeanList;
import com.blog.entity.BlogEntity;
import com.blog.entity.PublicUserCommentEntity;
import com.blog.repository.BlogRepository;
import com.blog.repository.PublicUserCommentsRepository;
import com.blog.service.CommentsSevice;

import jakarta.servlet.http.HttpSession;

@Service
public class CommentsServiceImpl implements CommentsSevice {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private PublicUserCommentsRepository commentsRepository;
	
	@Autowired
	private BlogRepository blogRepository;

	@Override
	public String addComment(Integer blogId, CommentForm commentForm) {
		
		PublicUserCommentEntity commentEntity=new PublicUserCommentEntity();
		
		BlogEntity byId = this.blogRepository.findById(blogId).orElse(null);
		if(byId==null) {
			return "Invalid Blog Id and With Given Blog Id , Blog Not Found";
		}else {
			BeanUtils.copyProperties(commentForm, commentEntity);
			commentEntity.setBlog(byId);
			commentEntity.setRemovalStatus("Active");
		    commentsRepository.save(commentEntity);
		    return "Comment Saved Successfully for '"+byId.getBlogTitle()+ "'  Blog!";
			
		}
	}
	
	
	@Override
	public Object fetchListOfCommentsOfLoggedInUser() {
		// TODO Find List Of Blogs Logged In User
		Object object = httpSession.getAttribute("LoggedInUserId");
		if (object == null) {
			return "please loggin and try again";
		}
		String string = object.toString();
		int int1 = Integer.parseInt(string);
		Integer loggedInUserIId = Integer.valueOf(int1);

		List<BlogEntity> listOfBlogs = this.blogRepository.findByUserUserId(loggedInUserIId);
		
		List<Integer> blogIdsOfLoggedInUser = listOfBlogs.stream().map(o->o.getBlogId()).collect(Collectors.toList());
		
	    //TODO : Find All Comments of All BLOGS
		
		List<PublicUserCommentEntity> all = this.commentsRepository.findAll();
		
		List<Integer> allCommentsBlogIds = all.stream().map(p->p.getBlog().getBlogId()).collect(Collectors.toList());
		
		List<Integer> duplicatesRemovedList = allCommentsBlogIds.stream().distinct().collect(Collectors.toList());
	    
		//TODO : GET Matching Blog ids from listOfCommentsBlogsIds, campare the listOfBlogIds with Commenst table blogs ids and get the matching ids 
		
		List<Integer> matchingBlogIdsPfListOfComments=new ArrayList<>();
		
		for(Integer i=0;i<=duplicatesRemovedList.size()-1;i++) {
			for(Integer j=0;j<=blogIdsOfLoggedInUser.size()-1;j++) {
				if(duplicatesRemovedList.get(i).equals(blogIdsOfLoggedInUser.get(j))) {
					matchingBlogIdsPfListOfComments.add(duplicatesRemovedList.get(i));
					
				}
				
			} 
		}
	      
		//TODO : Write a query to get list comments of mathcingBlogIds in from comments table
		
		List<PublicUserCommentEntity> fetchListOfCommentsOfLoggedInUser = this.commentsRepository.fetchListOfCommentsOfLoggedInUser(matchingBlogIdsPfListOfComments,"Active");
		
		List<CommetsUserResponseBean> commetsUserResponseBeans=new ArrayList<>();
		
		CommetsUserResponseBean commetsUserResponseBean;
		
		for(PublicUserCommentEntity list:fetchListOfCommentsOfLoggedInUser) {
			
			commetsUserResponseBean=new CommetsUserResponseBean();
			commetsUserResponseBean.setCommentId(list.getCommentId());
			commetsUserResponseBean.setEmail(list.getEmailIdOfEnduser());
			commetsUserResponseBean.setComment(list.getCommnetText());
			commetsUserResponseBean.setCreatedOn(list.getCreatedOn());
			
			commetsUserResponseBeans.add(commetsUserResponseBean);
			
		}
		
		CommetsUserResponseBeanList beanList=new CommetsUserResponseBeanList();
		beanList.setList(commetsUserResponseBeans);
		
		return beanList;
	}
	
	 @Override
	public Boolean softedeleteOfCommentByCmtID(Integer commentId) {
		
		 PublicUserCommentEntity byId = this.commentsRepository.findById(commentId).orElse(null);
		 if(byId==null) {
			 return false;
		 }
		 
		 byId.setRemovalStatus("InActive");
		 this.commentsRepository.save(byId);
		 return true;
	}
	
	
	
	
	
	
	

}

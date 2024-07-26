package com.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.binding.BlogForm;
import com.blog.binding.BlogListResponse;
import com.blog.binding.BlogResponse;
import com.blog.binding.BlogResponseBean;
import com.blog.binding.CommentResponse;
import com.blog.binding.ResponseBlogOfAllUsers;
import com.blog.binding.ResponseBlogOfAllUsersList;
import com.blog.entity.BlogEntity;
import com.blog.entity.PublicUserCommentEntity;
import com.blog.entity.UserDetailsEntity;
import com.blog.repository.BlogRepository;
import com.blog.repository.PublicUserCommentsRepository;
import com.blog.repository.UserDetailsRepository;
import com.blog.service.BlogDetailsService;
import com.blog.utils.LoggedInUserDetailsUtil;

import jakarta.servlet.http.HttpSession;

@Service
public class BlogDetailsServiceImpl implements BlogDetailsService {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private UserDetailsRepository detailsRepository;
	
	@Autowired
	private PublicUserCommentsRepository commentsRepository;
	
	@Autowired
	private LoggedInUserDetailsUtil detailsUtil;

	@Override
	public Boolean addBlog(BlogForm blogForm) {

		Object object = httpSession.getAttribute("LoggedInUserId");
		if (object == null) {
			return false;
		}
		String string = object.toString();
		int int1 = Integer.parseInt(string);
		Integer loggedInUserIId = Integer.valueOf(int1);

		BlogEntity blogEntity = new BlogEntity();

		BeanUtils.copyProperties(blogForm, blogEntity);

		Optional<UserDetailsEntity> userDetailsEntity = this.detailsRepository.findById(loggedInUserIId);
		if (userDetailsEntity == null) {
			return false;
		}
		UserDetailsEntity user = userDetailsEntity.get();

		blogEntity.setUser(user);
		blogEntity.setBlogRemovalStatus("active");

		this.blogRepository.save(blogEntity);
		
		

		return true;
	}

	@Override
	public Object fetchBlogsLoggedUser() {

		Object attribute = this.httpSession.getAttribute("LoggedInUserId");
		if (attribute == null) {
			return "User Not LoggedIn";
		}
		String string = attribute.toString();
		int int1 = Integer.parseInt(string);
		Integer userId = Integer.valueOf(int1);

		//List<BlogEntity> list = this.blogRepository.findByUserUserId(userId);
		List<BlogEntity> list = this.blogRepository.findByBlogRemovalStatusAndUserUserId("active", userId);
		
		List<BlogResponse> blogResponses = new ArrayList<>();

		BlogResponse blogBean;

		for (BlogEntity blogs : list) {
			blogBean = new BlogResponse();
			blogBean.setBlogId(blogs.getBlogId());
			blogBean.setBlogTitle(blogs.getBlogTitle());
			blogBean.setBlogShortDes(blogs.getBlogShortDescription());
			blogBean.setBlogCreatedDate(blogs.getCreatedDate());
			blogResponses.add(blogBean);
		}

		BlogListResponse blogListResponse = new BlogListResponse();
		blogListResponse.setBlogResponse(blogResponses);

		return blogListResponse;
	}

	@Override
	public Object getBlogsOfUserLoggedIn() {
		// TODO Auto-generated method stub
		Integer loggedInUserId = detailsUtil.loggedInUserId();
		List<BlogResponseBean> fetchListOfBlogsByCustomQuery = this.blogRepository.fetchListOfBlogsByCustomQuery(loggedInUserId);
		return fetchListOfBlogsByCustomQuery;
	}
	
	
	@Override
	public Object updateBlog(Integer blogId,BlogForm blogForm) {
		// TODO Fetch The Records based on given blog id and update it the record as user wants to
		
		BlogEntity blogEntity = this.blogRepository.findById(blogId).orElse(null);
		
		if(blogEntity==null) {
			return "Blog not Found With Given Blog Id";
		}
	
		Object attribute = this.httpSession.getAttribute("LoggedInUserId");
		if(attribute==null) {
			return "Please do Login again because your session has expired!!";
		}
		String string = attribute.toString();
		int int1 = Integer.parseInt(string);
		Integer loggedInUserId = Integer.valueOf(int1);
		
		//Integer loggedInUserId = this.detailsUtil.loggedInUserId();
		
//		if(loggedInUserId==null) {
//			return "Please do Login again because your session has expired!!";
//		}
		
		if(blogEntity.getUser().getUserId().equals(loggedInUserId)) {
		BeanUtils.copyProperties(blogForm, blogEntity);
		 BlogEntity save = this.blogRepository.save(blogEntity);
		 
		 return "successfully updated the record";
			
		}else {
			return "Sorry your are not supposed to update the records because, your modifying other users record";
		} 
	
	}
	
	@Override
	public Object fetchBlogsOfUsers() {
		
		List<BlogEntity> list = this.blogRepository.findAll();
		List<ResponseBlogOfAllUsers> listOfBlogsResponse=new ArrayList<>();
		ResponseBlogOfAllUsersList allUsersList=new ResponseBlogOfAllUsersList();
		ResponseBlogOfAllUsers response;
		for(BlogEntity entity:list) {
			response=new ResponseBlogOfAllUsers();
			response.setBlogTitle(entity.getBlogTitle());
			response.setCreatedDate(entity.getCreatedDate());
			response.setBlogShortDescription(entity.getBlogShortDescription());
			listOfBlogsResponse.add(response);
		}
		allUsersList.setBlogOfAllUsers(listOfBlogsResponse);
		if(allUsersList==null) {
			return "NO BLOGS FOUND";
		}
		
		return allUsersList;
	}
	
	
	@Override
	public Boolean deleteBlogByBlogId(Integer blogId) {
		// TODO Fetch the blog with given Blog Id and make Blog as In-Active
		
		BlogEntity blogEntity = this.blogRepository.findById(blogId).orElse(null);
		List<PublicUserCommentEntity> listOfCommentsOfBlog = this.commentsRepository.findByBlogBlogId(blogEntity.getBlogId());
		if(blogEntity==null) {
			return false;
		}else {
			blogEntity.setBlogRemovalStatus("InActive");
			this.blogRepository.save(blogEntity);
			
			ListIterator<PublicUserCommentEntity> listIterator = listOfCommentsOfBlog.listIterator();
			while(listIterator.hasNext()) {
                    PublicUserCommentEntity commentEntity = listIterator.next();
				    commentEntity.setRemovalStatus("InActive");
                    this.commentsRepository.save(commentEntity);        
			}
		    return true;
		}
	
	}

	
	@Override
	public Object fetchCommentsOfBlogId(Integer blogId) {
	 
		Object attribute = httpSession.getAttribute("LoggedInUserId");
		if(attribute==null){
          return "please loggin and try again";			
		}
		String string = attribute.toString();
		int int1 = Integer.parseInt(string);
		Integer userdIdLogged = Integer.valueOf(int1);
		
		
		List<PublicUserCommentEntity> byBlogBlogId = this.commentsRepository.findByBlogBlogIdAndRemovalStatus(blogId,"Active");
		if(byBlogBlogId.isEmpty()) {
			return "Blogs Not Found With Given Blog Id";
		}
		
		List<CommentResponse> list=new ArrayList<>();
		CommentResponse commentResponse;
		for(PublicUserCommentEntity listOfBlogs:byBlogBlogId) {
			commentResponse =new CommentResponse();
			commentResponse.setNameOfUser(listOfBlogs.getNameOfUser());
			commentResponse.setCreatedOn(listOfBlogs.getCreatedOn());
			commentResponse.setCommentText(listOfBlogs.getCommnetText());
 
			list.add(commentResponse);
		}

		return list;
	}
	
	@Override
	public Object fetchBlogsBySearchFunctionality(String search) {
		
		String patternSearch="%"+search+"%";
		
		List<BlogEntity> blogsBySeachKeyWord = this.blogRepository.findBlogsBySeachKeyWord(patternSearch);
		if(blogsBySeachKeyWord.isEmpty()) {
			return "No Blogs Found";		
		}
		
		List<ResponseBlogOfAllUsers> listOfBlogsResponse=new ArrayList<>();
		ResponseBlogOfAllUsersList allUsersList=new ResponseBlogOfAllUsersList();
		ResponseBlogOfAllUsers response;
		
		for(BlogEntity blogEntity:blogsBySeachKeyWord) {
			response=new ResponseBlogOfAllUsers();
			response.setBlogTitle(blogEntity.getBlogTitle());
			response.setCreatedDate(blogEntity.getCreatedDate());
			response.setBlogShortDescription(blogEntity.getBlogShortDescription());
			
			listOfBlogsResponse.add(response);
		
		}
		
         allUsersList.setBlogOfAllUsers(listOfBlogsResponse);
		
		return listOfBlogsResponse;
	}
	
	
    @Override
	public Object fetchBlogsLoggedUserWithSearchFun(String search) {
		// TODO Auto-generated method stub
    	
    	Object attribute = this.httpSession.getAttribute("LoggedInUserId");
		if (attribute == null) {
			return "User Not LoggedIn";
		}
		String string = attribute.toString();
		int int1 = Integer.parseInt(string);
		Integer userId = Integer.valueOf(int1);
    	
    	List<BlogEntity> byBlogRemovalStatusAndUserUserId = this.blogRepository.findByBlogRemovalStatusAndUserUserId("Active", userId);
    	List<BlogEntity> collect = byBlogRemovalStatusAndUserUserId.stream().filter(p->p.getBlogTitle().contains(search)).collect(Collectors.toList());
    	
    	List<BlogEntity> blogEntities=new ArrayList<>();
    	
    	ListIterator<BlogEntity> listIterator = byBlogRemovalStatusAndUserUserId.listIterator();
    	
    	while(listIterator.hasNext()) {
    		BlogEntity next = listIterator.next();
    		if(next.getBlogTitle().toUpperCase().contains(search.toUpperCase())){
    			blogEntities.add(next);
    		}
    	}
    	
    	List<ResponseBlogOfAllUsers> listOfBlogsResponse=new ArrayList<>();
		ResponseBlogOfAllUsersList allUsersList=new ResponseBlogOfAllUsersList();
		ResponseBlogOfAllUsers response;
    	for(BlogEntity   blogs:blogEntities) {
    		response=new ResponseBlogOfAllUsers();
    		response.setBlogTitle(blogs.getBlogTitle());
    		response.setCreatedDate(blogs.getCreatedDate());
    		response.setBlogShortDescription(blogs.getBlogShortDescription());
    		
    		listOfBlogsResponse.add(response);
    		
    	}
    	
    	allUsersList.setBlogOfAllUsers(listOfBlogsResponse);
 
		return allUsersList;
	}
	
	
}

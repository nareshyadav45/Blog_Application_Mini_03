package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.blog.binding.BlogResponseBean;
import com.blog.entity.BlogEntity;
import jakarta.persistence.Tuple;

public interface BlogRepository extends JpaRepository<BlogEntity, Integer>{
	
	public List<BlogEntity> findByUserUserId(Integer userId);
	
	@Query("SELECT b.blogId,b.blogTitle,b.blogShortDescription,b.createdDate FROM BlogEntity AS b WHERE b.user.userId = :userid")
	public List<BlogEntity> fetchListOfBlogsByUserId(@Param("userid") Integer userId);
	
	@Query("SELECT b.blogId as blogId, b.blogTitle as blogTitle, b.blogShortDescription as blogShortDescription, b.createdDate as createdDate FROM BlogEntity AS b WHERE b.user.userId = :userid")
	public List<Tuple> fetchListOfBlogsByUserIdTUPLE(@Param("userid") Integer userId);
	
	@Query("SELECT new com.blog.binding.BlogResponseBean(b.blogId,b.blogTitle,b.blogShortDescription,b.createdDate) FROM BlogEntity AS b WHERE b.user.userId = : userid")
	public List<BlogResponseBean> fetchListOfBlogsByCustomQuery(@Param("userid") Integer userId);
	
	public List<BlogEntity> findByBlogRemovalStatusAndUserUserId(String removalStatus,Integer userId);
	
	@Query("SELECT v FROM BlogEntity AS v WHERE v.blogTitle LIKE :search")
	public List<BlogEntity> findBlogsBySeachKeyWord(@Param("search") String searchWord);
	
	
	
}

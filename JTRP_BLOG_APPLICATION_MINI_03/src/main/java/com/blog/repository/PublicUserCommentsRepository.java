package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entity.PublicUserCommentEntity;

public interface PublicUserCommentsRepository extends JpaRepository<PublicUserCommentEntity, Integer>{

	public List<PublicUserCommentEntity> findByBlogBlogIdAndRemovalStatus(Integer blogId,String status);
	
	public List<PublicUserCommentEntity> findByBlogBlogId(Integer blogId);
	
	@Query("SELECT c FROM PublicUserCommentEntity AS c WHERE c.blog.blogId IN :blogIds AND c.removalStatus = :removalStatusOfComment")
	public List<PublicUserCommentEntity> fetchListOfCommentsOfLoggedInUser(@Param("blogIds") List<Integer> list,@Param("removalStatusOfComment") String removalStatus );

	
	
	
	
	
}

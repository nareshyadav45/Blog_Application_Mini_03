package com.blog.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="blog_table")
public class BlogEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;
	
	@Column(name="blog_title")
	private String blogTitle;
	
	@Column(name="blog_short_descrip")
	private String blogShortDescription;
	
	@Column(name="blog_content")
	@Lob
	private String content;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetailsEntity user;
	
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	@OneToMany(mappedBy = "blog",cascade = CascadeType.REMOVE)
	private List<PublicUserCommentEntity> listOfcomments;
	
	@Column(name="blog_removal_status")
	private String blogRemovalStatus;

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

	public String getBlogShortDescription() {
		return blogShortDescription;
	}

	public void setBlogShortDescription(String blogShortDescription) {
		this.blogShortDescription = blogShortDescription;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserDetailsEntity getUser() {
		return user;
	}

	public void setUser(UserDetailsEntity user) {
		this.user = user;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<PublicUserCommentEntity> getListOfcomments() {
		return listOfcomments;
	}

	public void setListOfcomments(List<PublicUserCommentEntity> listOfcomments) {
		this.listOfcomments = listOfcomments;
	}

	public String getBlogRemovalStatus() {
		return blogRemovalStatus;
	}

	public void setBlogRemovalStatus(String blogRemovalStatus) {
		this.blogRemovalStatus = blogRemovalStatus;
	} 
	
	
	
	
	
}

package com.blog.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="public_user_comments_table")
public class PublicUserCommentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	
	@ManyToOne
	@JoinColumn(name="blog_id")
	private BlogEntity blog;
	
	private String emailIdOfEnduser;
	
	private String nameOfUser;
	
	@Lob
	private String commnetText;
	
	@CreationTimestamp
	private LocalDate createdOn;
	
	@Column(name="comment_removal_status")
	private String removalStatus;
	
	public String getRemovalStatus() {
		return removalStatus;
	}

	public void setRemovalStatus(String removalStatus) {
		this.removalStatus = removalStatus;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public BlogEntity getBlog() {
		return blog;
	}

	public void setBlog(BlogEntity blog) {
		this.blog = blog;
	}

	public String getEmailIdOfEnduser() {
		return emailIdOfEnduser;
	}

	public void setEmailIdOfEnduser(String emailIdOfEnduser) {
		this.emailIdOfEnduser = emailIdOfEnduser;
	}

	public String getNameOfUser() {
		return nameOfUser;
	}

	public void setNameOfUser(String nameOfUser) {
		this.nameOfUser = nameOfUser;
	}

	public String getCommnetText() {
		return commnetText;
	}

	public void setCommnetText(String commnetText) {
		this.commnetText = commnetText;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}
	
	
	

}

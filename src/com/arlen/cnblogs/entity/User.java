package com.arlen.cnblogs.entity;

import java.net.URL;
import java.util.Date;

public class User {
	public URL id;
	public String title;
	public Date updatedDate;
	public URL userLink;
	public String blogapp;
	public URL userAvatar;
	public int postCount;

	public URL getId() {
		return id;
	}

	public void setId(URL id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public URL getUserLink() {
		return userLink;
	}

	public void setUserLink(URL userLink) {
		this.userLink = userLink;
	}

	public String getBlogapp() {
		return blogapp;
	}

	public void setBlogapp(String blogapp) {
		this.blogapp = blogapp;
	}

	public URL getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(URL userAvatar) {
		this.userAvatar = userAvatar;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof User) {
			User user = (User) object;
			return String.valueOf(user.getTitle()).equals(
					String.valueOf(this.getTitle()));
		} else {
			return super.equals(object);
		}
	}
}

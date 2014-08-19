package com.arlen.cnblogs.entity;

import java.net.URL;
import java.util.Date;

public class Blog {
	private int blogId;
	private String blogTitle;
	private String blogSummary;
	private Date publishedDate;
	private Date updatedDate;
	private String authorName;
	private URL authorUri;
	private URL authorAvatar;
	private URL blogLink;
	private String blogApp;
	private int blogDiggs;
	private int blogViews;
	private int blogComments;

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogSummary() {
		return blogSummary;
	}

	public void setBlogSummary(String blogSummary) {
		this.blogSummary = blogSummary;
	}

	public Date getPublishedDateDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publisheDate) {
		this.publishedDate = publisheDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public URL getAuthorUri() {
		return authorUri;
	}

	public void setAuthorUri(URL authorUri) {
		this.authorUri = authorUri;
	}

	public URL getAuthorAvatar() {
		return authorAvatar;
	}

	public void setAuthorAvatar(URL authorAvatar) {
		this.authorAvatar = authorAvatar;
	}

	public URL getBlogLink() {
		return blogLink;
	}

	public void setBlogLink(URL blogLink) {
		this.blogLink = blogLink;
	}

	public String getBlogApp() {
		return blogApp;
	}

	public void setBlogApp(String blogApp) {
		this.blogApp = blogApp;
	}

	public int getBlogDiggs() {
		return blogDiggs;
	}

	public void setBlogDiggs(int blogDiggs) {
		this.blogDiggs = blogDiggs;
	}

	public int getBlogViews() {
		return blogViews;
	}

	public void setBlogViews(int blogViews) {
		this.blogViews = blogViews;
	}

	public int getBlogComments() {
		return blogComments;
	}

	public void setBlogComments(int blogComments) {
		this.blogComments = blogComments;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Blog) {
			Blog blog = (Blog) object;
			return String.valueOf(blog.getBlogId()).equals(
					String.valueOf(this.getBlogId()));
		} else {
			return super.equals(object);
		}
	}
}

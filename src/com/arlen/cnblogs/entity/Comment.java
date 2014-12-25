package com.arlen.cnblogs.entity;

import java.net.URL;
import java.util.Date;

public class Comment {
	private int commentId;
	private String commentTitle;
	private Date publishedDate;
	private Date updatedDate;
	private String authorName;
	private URL authorUri;
	private String commentContent;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentTitle() {
		return commentTitle;
	}

	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
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

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	// public boolean equals(Object object) {
	// if (object instanceof Blog) {
	// Blog blog = (Blog) object;
	// return String.valueOf(blog.getBlogId()).equals(
	// String.valueOf(this.getBlogId()));
	// } else {
	// return super.equals(object);
	// }
	// }

	@Override
	public boolean equals(Object object) {
		if (object instanceof Comment) {
			Comment comment = (Comment) object;
			return String.valueOf(comment.getCommentId()).equals(
					String.valueOf(this.getCommentId()));
		} else {
			return super.equals(object);
		}
	}
}

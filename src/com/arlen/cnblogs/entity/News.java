package com.arlen.cnblogs.entity;

import java.net.URL;
import java.util.Date;

public class News {
	private int newsId;
	private String newsTitle;
	private String newsSummary;
	private Date publishedDate;
	private Date updatedDate;
	private URL newsLink;
	private int newsDiggs;
	private int newsViews;
	private int newsComments;
	private String newsTopic;
	private URL topicIcon;
	private String sourceName;

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsSummary() {
		return newsSummary;
	}

	public void setNewsSummary(String newsSummary) {
		this.newsSummary = newsSummary;
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

	public void setUpdatedDate(Date updateDate) {
		this.updatedDate = updateDate;
	}

	public URL getNewsLink() {
		return newsLink;
	}

	public void setNewsLink(URL newsLink) {
		this.newsLink = newsLink;
	}

	public int getNewsDiggs() {
		return newsDiggs;
	}

	public void setNewsDiggs(int newsDiggs) {
		this.newsDiggs = newsDiggs;
	}

	public int getNewsViews() {
		return newsViews;
	}

	public void setNewsViews(int newsViews) {
		this.newsViews = newsViews;
	}

	public int getNewsComments() {
		return newsComments;
	}

	public void setNewsComments(int newsComments) {
		this.newsComments = newsComments;
	}

	public String getNewsTopic() {
		return newsTopic;
	}

	public void setNewsTopic(String newsTopic) {
		this.newsTopic = newsTopic;
	}

	public URL getTopicIcon() {
		return topicIcon;
	}

	public void setTopicIcon(URL topicIcon) {
		this.topicIcon = topicIcon;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

}

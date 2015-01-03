package com.arlen.cnblogs.bean;

public class PersonInfo {
	private String nickName;
	private String cnblogsAge;
	private int followers;
	private int followees;

	public PersonInfo() {
		super();
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCnblogsAge() {
		return cnblogsAge;
	}

	public void setCnblogsAge(String cnblogsAge) {
		this.cnblogsAge = cnblogsAge;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getFollowees() {
		return followees;
	}

	public void setFollowees(int followees) {
		this.followees = followees;
	}

}

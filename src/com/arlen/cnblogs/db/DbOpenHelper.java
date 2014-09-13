package com.arlen.cnblogs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	public DbOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlBlog = "CREATE TABLE [blog] ( [blogId] INT NOT NULL PRIMARY KEY, [blogTitle] TEXT, [blogSummary] TEXT, [publishedDate] TEXT, [updatedDate] TEXT, [authorName] TEXT, [authorUri] TEXT, [authorAvatar] TEXT, [blogLink] TEXT, [blogApp] TEXT, [blogDiggs] INT DEFAULT 0, [blogViews] INT DEFAULT 0, [blogComments] INT DEFAULT 0);";
		String sqlNews = "CREATE TABLE [news] ( [newsId] INT NOT NULL PRIMARY KEY, [newsTitle] TEXT, [newsSummary] TEXT, [publishedDate] TEXT, [updatedDate] TEXT, [newsLink] TEXT, [newsDiggs] INT DEFAULT 0, [newsViews] INT DEFAULT 0, [newsComments] INT DEFAULT 0, [newsTopic] TEXT, [topicIcon] TEXT, [sourceName] TEXT);";
		String sqlUser = "CREATE TABLE [user] ( [id] INT NOT NULL PRIMARY KEY, [title] TEXT, [updatedDate] TEXT, [blogapp] TEXT, [userAvatar] TEXT, [postCount] INT DEFAULT 0);";
		String sqlComment = "CREATE TABLE [comment] ( [commentId] INT NOT NULL PRIMARY KEY, [commentTitle] TEXT, [publishedDate] TEXT, [updatedDate] TEXT, [authorName] TEXT, [authorUri] TEXT, [commentContent] TEXT);";
		String sqlCookies = "CREATE TABLE [cookies] ( [cookiesId] INT NOT NULL PRIMARY KEY, [userName] TEXT, [updatedDate] TEXT, [cookieContent] TEXT);";
		db.execSQL(sqlBlog);
		db.execSQL(sqlNews);
		db.execSQL(sqlUser);
		db.execSQL(sqlComment);
		db.execSQL(sqlCookies);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onDowngrade(db, oldVersion, newVersion);
	}

}

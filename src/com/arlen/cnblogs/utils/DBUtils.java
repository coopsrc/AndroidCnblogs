package com.arlen.cnblogs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.arlen.cnblogs.dao.BlogDao;
import com.arlen.cnblogs.dao.CookieDao;
import com.arlen.cnblogs.dao.DbOpenHelper;
import com.arlen.cnblogs.entity.Blog;

public class DBUtils {
	public static void createDB(Context context) {
		DbOpenHelper helper = new DbOpenHelper(context, AppMacros.DATABASE_NAME,
				null, AppMacros.DATABASE_VERSION);
		helper.getWritableDatabase();
	}

	public static void addBlog(Context context, Blog blog) {
		BlogDao blogDao = new BlogDao(context);
		Object[] params = { blog.getBlogId(), blog.getBlogTitle(),
				blog.getBlogSummary(),
				AppUtils.parseDateToString(blog.getPublishedDateDate()),
				AppUtils.parseDateToString(blog.getUpdatedDate()),
				blog.getAuthorName(), blog.getAuthorUri().toString(),
				blog.getAuthorAvatar().toString(),
				blog.getBlogLink().toString(), blog.getBlogApp(),
				blog.getBlogDiggs(), blog.getBlogViews(),
				blog.getBlogComments() };
		blogDao.addData(params);
	}

	public static void addBlog(Context context, List<Blog> blogList) {
		for (Blog blog : blogList) {
			addBlog(context, blog);
		}
	}

	public static void deleteBlog(Context context, Blog blog) {
		BlogDao blogDao = new BlogDao(context);
		Object[] params = { blog.getBlogId() };
		blogDao.deleteData(params);
	}

	public static void deleteBlog(Context context, List<Blog> blogList) {
		for (Blog blog : blogList) {
			deleteBlog(context, blog);
		}
	}

	public static void updateBlog(Context context, Blog blog) {
		BlogDao blogDao = new BlogDao(context);
		Object[] params = { blog.getBlogId(), blog.getBlogTitle(),
				blog.getBlogSummary(),
				AppUtils.parseDateToString(blog.getPublishedDateDate()),
				AppUtils.parseDateToString(blog.getUpdatedDate()),
				blog.getAuthorName(), blog.getAuthorUri().toString(),
				blog.getAuthorAvatar().toString(),
				blog.getBlogLink().toString(), blog.getBlogApp(),
				blog.getBlogDiggs(), blog.getBlogViews(),
				blog.getBlogComments(), blog.getBlogId() };
		blogDao.updateData(params);
	}

	public static void updateBlog(Context context, List<Blog> blogList) {
		for (Blog blog : blogList) {
			updateBlog(context, blog);
		}
	}

	public static Blog viewBlog(Context context, String[] selectionArgs) {
		BlogDao blogDao = new BlogDao(context);
		Map<String, String> map = blogDao.viewData(selectionArgs);
		Blog blog = new Blog();

		blog.setBlogId(Integer.parseInt(map.get("blogId")));
		blog.setBlogTitle(map.get("blogTitle"));
		blog.setBlogSummary(map.get("blogSummary"));
		blog.setPublishedDate(AppUtils.parseStringToDate(map
				.get("publishedDate")));
		blog.setUpdatedDate(AppUtils.parseStringToDate(map.get("updatedDate")));
		blog.setAuthorName(map.get("authorName"));
		blog.setAuthorUri(AppUtils.parseStringToUrl(map.get("authorUri")));
		blog.setAuthorAvatar(AppUtils.parseStringToUrl(map.get("authorAvatar")));
		blog.setBlogLink(AppUtils.parseStringToUrl(map.get("blogLink")));
		blog.setBlogApp(map.get("blogApp"));
		blog.setBlogDiggs(Integer.parseInt(map.get("blogDiggs")));
		blog.setBlogViews(Integer.parseInt(map.get("blogViews")));
		blog.setBlogComments(Integer.parseInt(map.get("blogComments")));

		return blog;
	}

	public static List<Blog> listBlog(Context context, String[] selectionArgs) {
		BlogDao blogDao = new BlogDao(context);
		List<Blog> blogList = new ArrayList<Blog>();
		List<Map<String, String>> mapList = blogDao.listData(selectionArgs);

		for (Map<String, String> map : mapList) {
			Blog blog = new Blog();

			blog.setBlogId(Integer.parseInt(map.get("blogId")));
			blog.setBlogTitle(map.get("blogTitle"));
			blog.setBlogSummary(map.get("blogSummary"));
			blog.setPublishedDate(AppUtils.parseStringToDate(map
					.get("publishedDate")));
			blog.setUpdatedDate(AppUtils.parseStringToDate(map
					.get("updatedDate")));
			blog.setAuthorName(map.get("authorName"));
			blog.setAuthorUri(AppUtils.parseStringToUrl(map.get("authorUri")));
			blog.setAuthorAvatar(AppUtils.parseStringToUrl(map
					.get("authorAvatar")));
			blog.setBlogLink(AppUtils.parseStringToUrl(map.get("blogLink")));
			blog.setBlogApp(map.get("blogApp"));
			blog.setBlogDiggs(Integer.parseInt(map.get("blogDiggs")));
			blog.setBlogViews(Integer.parseInt(map.get("blogViews")));
			blog.setBlogComments(Integer.parseInt(map.get("blogComments")));
			blogList.add(blog);
		}

		return blogList;
	}
	
	public static void addCookie(Context context, String userName, String cookie){
		CookieDao cookieDao = new CookieDao(context);
		Object[] params = {userName, cookie};
		cookieDao.addData(params);
	}
	
	public static String viewCookie(Context context, String[] selectionArgs){
		String cookie = "";
		CookieDao cookieDao = new CookieDao(context);
		Map<String, String> map = cookieDao.viewData(selectionArgs);
		cookie = map.get("cookie");
		return cookie;
	}
	
	public static List<String> listCookie(Context context, String[] selectionArgs){
		List<String> cookieList = new ArrayList<String>();
		CookieDao cookieDao = new CookieDao(context);
		List<Map<String, String>> mapList = cookieDao.listData(selectionArgs);
		for(Map<String, String> map : mapList){
			String cookie = "";
			cookie = map.get("cookie");
			cookieList.add(cookie);
		}
		return cookieList;
	}
}

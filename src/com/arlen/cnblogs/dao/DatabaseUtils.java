package com.arlen.cnblogs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.arlen.cnblogs.db.DbOpenHelper;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;

public class DatabaseUtils {
	public void createDB(Context context) {
		DbOpenHelper helper = new DbOpenHelper(context, Config.DATABASE_NAME,
				null, Config.DATABASE_VERSION);
		helper.getWritableDatabase();
	}

	public void addBlog(Context context, Blog blog) {
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

	public void addBlog(Context context, List<Blog> blogList) {
		BlogDao blogDao = new BlogDao(context);
		for (Blog blog : blogList) {
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
	}

	public void deleteBlog(Context context, Blog blog) {
		BlogDao blogDao = new BlogDao(context);
		Object[] params = { blog.getBlogId() };
		blogDao.deleteData(params);
	}

	public void deleteBlog(Context context, List<Blog> blogList) {
		BlogDao blogDao = new BlogDao(context);
		for (Blog blog : blogList) {
			Object[] params = { blog.getBlogId() };
			blogDao.deleteData(params);
		}
	}

	public void updateBlog(Context context, Blog blog) {
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

	public void updateBlog(Context context, List<Blog> blogList) {
		BlogDao blogDao = new BlogDao(context);
		for (Blog blog : blogList) {
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
	}

	public List<Blog> listBlog(Context context, String[] selectionArgs) {
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

	public Blog viewBlogById(Context context, String[] selectionArgs) {
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
}

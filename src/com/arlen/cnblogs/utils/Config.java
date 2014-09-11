package com.arlen.cnblogs.utils;

public class Config {
	// 软件作者
	public final static String AUTHOR_NAME = "Zhang Tingkuo";
	public final static String AUTHOR_EMAIL = "1757887521@qq.com";

	// 邮件(反馈信息)
	public final static String MAIL_SERVER_HOST = "smtp.163.com";
	public final static String MAIL_SERVER_PORT = "25";
	public final static String MAIL_ACCUNT = "cnblogs@163.com";
	public final static String MAIL_PASSWORD = "Cnblogs.com";
	public final static String MAIL_SUBJECT = "Cnblogs反馈信息(Android)";

	// 日期格式
	public final static String SIMPLE_DATA_FORMATE = "yyyy-MM-dd hh:mm:ss";

	// 临时图片文件
	public static final String TEMP_IMAGES_LOCATION = "Cnblogs/images/";

	// 博客
	// 博客分页大小
	public static final int BLOG_PAGE_SIZE = 15;
	// 48小时阅读排行
	public static final String TOP_VIEW_POSTS_48_HOUR = "http://wcf.open.cnblogs.com/blog/48HoursTopViewPosts/{ITEMCOUNT}";
	// 分页获取推荐博客列表(作者)
	public static final String RECOMMEND_BLOGS_PAGED = "http://wcf.open.cnblogs.com/blog/bloggers/recommend/{PAGEINDEX}/{PAGESIZE}";
	// 获取推荐博客总数
	public static final String RECOMMEND_BLOGS_COUNT = "http://wcf.open.cnblogs.com/blog/bloggers/recommend/count";
	// 根据作者名搜索博主
	public static final String SEARCH_AUTHOR_BY_NAME = "http://wcf.open.cnblogs.com/blog/bloggers/search?t={TERM}";
	// 获取文章评论
	public static final String BLOGS_COMMENTS = "http://wcf.open.cnblogs.com/blog/post/{POSTID}/comments/{PAGEINDEX}/{PAGESIZE}";
	// 获取文章内容
	public static final String BLOGS_CONTENTS = "http://wcf.open.cnblogs.com/blog/post/body/{POSTID}";
	// 获取首页文章列表
	public static final String RECENT_BLOGS = "http://wcf.open.cnblogs.com/blog/sitehome/recent/{ITEMCOUNT}";
	// 分页获取首页文章列表
	public static final String RECENT_BLOGS_PAGED = "http://wcf.open.cnblogs.com/blog/sitehome/paged/{PAGEINDEX}/{PAGESIZE}";
	// 10天内推荐排行
	public static final String TEN_DAYS_TOP_DIGG_POSTS = "http://wcf.open.cnblogs.com/blog/TenDaysTopDiggPosts/{ITEMCOUNT}";
	// 分页获取个人博客文章列表
	public static final String USER_BLOGS_LIST_PAGED = "http://wcf.open.cnblogs.com/blog/u/{BLOGAPP}/posts/{PAGEINDEX}/{PAGESIZE}";

	// 新闻
	// 新闻分页大小
	public static final int News_PAGE_SIZE = 30;
	// 获取新闻列表
	public static final String NEWS_LIST = "http://wcf.open.cnblogs.com/news/GetData";
	// 获取热门新闻列表
	public static final String HOT_NEWS_LIST = "http://wcf.open.cnblogs.com/news/hot/{ITEMCOUNT}";
	// 获取新闻内容
	public static final String NEWS_CONTENT = "http://wcf.open.cnblogs.com/news/item/{CONTENTID}";
	// 获取新闻评论
	public static final String NEWS_COMMENTS = "http://wcf.open.cnblogs.com/news/item/{CONTENTID}/comments/{PAGEINDEX}/{PAGESIZE}";
	// 获取最新新闻列表
	public static final String RECENT_NEWS = "http://wcf.open.cnblogs.com/news/recent/{ITEMCOUNT}";
	// 分页获取最新新闻列表
	public static final String RECENT_NEWS_PAGED = "http://wcf.open.cnblogs.com/news/recent/paged/{PAGEINDEX}/{PAGESIZE}";
	// 分页获取推荐新闻列表
	public static final String RECOMMEND_NEWS_PAGED = "http://wcf.open.cnblogs.com/news/recommend/paged/{PAGEINDEX}/{PAGESIZE}";

	// 评论
	public static final int COMMENT_PAGE_SIZE = 10;

}

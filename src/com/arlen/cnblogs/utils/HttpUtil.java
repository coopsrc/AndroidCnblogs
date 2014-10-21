package com.arlen.cnblogs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.util.Log;

import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.entity.Comment;
import com.arlen.cnblogs.entity.News;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.handler.BlogItemHandler;
import com.arlen.cnblogs.handler.BlogListHandler;
import com.arlen.cnblogs.handler.CommentListHandler;
import com.arlen.cnblogs.handler.NewsItemHandler;
import com.arlen.cnblogs.handler.NewsListHandler;
import com.arlen.cnblogs.handler.UserListHandler;

public class HttpUtil {

	public static InputStream getXmlStream(String path) {
		InputStream inputStream = null;
		Log.i("getXmlStream", "获取XML InputStream    " + path);
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setReadTimeout(3 * 1000);
			connection.setConnectTimeout(5 * 1000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();
			int code = connection.getResponseCode();
			Log.d("getXmlStream", "ResponseCode : " + code);
			if (code == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
			}

			if (inputStream != null) {
				Log.i("getXmlStream", "获取成功");
			}

		} catch (Exception e) {
			Log.e("getXmlStream", "获取失败");
			e.printStackTrace();
		}
		return inputStream;
	}

	public static List<Blog> getBlogList(String path) {
		Log.i("getBlogList", "获取博客列表 XML" + path);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			BlogListHandler handler = new BlogListHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStream(path);
			InputSource inputSource = new InputSource(inputStream);
			reader.parse(inputSource);
			Log.i("getBlogList", "获取博客列表 XML 完成");
			return handler.getBlogList();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getBlogContent(String path) {
		String blogContent = "";
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			BlogItemHandler handler = new BlogItemHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStream(path);
			InputSource inputSource = new InputSource(inputStream);
			reader.parse(inputSource);
			Log.i("getBlogList", "获取博客列表 XML 完成");
			return handler.getBlogContent();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blogContent;
	}

	public static List<User> getUserList(String path) {
		Log.i("getBlogList", "获取博客列表 XML" + path);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			UserListHandler handler = new UserListHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStream(path);
			InputSource inputSource = new InputSource(inputStream);
			reader.parse(inputSource);
			Log.i("getBlogList", "获取博客列表 XML 完成");
			return handler.getUserList();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<News> getNewsList(String path) {
		Log.i("getBlogList", "获取博客列表 XML" + path);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			NewsListHandler handler = new NewsListHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStream(path);
			InputSource inputSource = new InputSource(inputStream);
			reader.parse(inputSource);
			Log.i("getBlogList", "获取博客列表 XML 完成");
			return handler.getNewsList();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNewsContent(String path) {
		String newsContent = "";
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			NewsItemHandler handler = new NewsItemHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStream(path);
			InputSource inputSource = new InputSource(inputStream);
			reader.parse(inputSource);
			Log.i("getBlogList", "获取博客列表 XML 完成");
			return handler.getNewsContent();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newsContent;
	}
	
	public static List<Comment> getCommentList(String path) {
		Log.i("getBlogList", "获取博客列表 XML" + path);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			CommentListHandler handler = new CommentListHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStream(path);
			InputSource inputSource = new InputSource(inputStream);
			reader.parse(inputSource);
			Log.i("getBlogList", "获取博客列表 XML 完成");
			return handler.getCommentList();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

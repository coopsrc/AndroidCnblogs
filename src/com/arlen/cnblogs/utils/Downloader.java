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
import com.arlen.cnblogs.handler.BlogListHandler;

public class Downloader {

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
}

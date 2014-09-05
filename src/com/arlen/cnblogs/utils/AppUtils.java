package com.arlen.cnblogs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.handler.BlogItemHandler;
import com.arlen.cnblogs.handler.BlogListHandler;
import com.arlen.cnblogs.handler.UserListHandler;
import com.arlen.cnblogs.mail.MailSenderInfo;
import com.arlen.cnblogs.mail.SimpleMailSender;

public class AppUtils {

	/**
	 * 将字符串转换为URL类型
	 * 
	 * @param string
	 * @return URL
	 */
	public static URL parseStringToUrl(String string) {
		URL url = null;
		try {
			Log.i("parseStringToUrl", "转换:" + string);
			url = new URL(string);
			Log.i("parseStringToUrl", "转换成功");
		} catch (MalformedURLException e) {
			Log.e("parseStringToUrl", "转换失败");
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * 将String转换为Date类型
	 * 
	 * @param string
	 * @return Date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseStringToDate(String string) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Config.SIMPLE_DATA_FORMATE);
		Date date = null;
		try {
			date = dateFormat.parse(string);
		} catch (ParseException e) {
			Log.e("parseStringToDate", "转换失败");
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将Date转换为String类型
	 * 
	 * @param date
	 * @return String
	 */
	@SuppressLint("SimpleDateFormat")
	public static String parseDateToString(Date date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Config.SIMPLE_DATA_FORMATE);
			return dateFormat.format(date);
		} catch (Exception e) {
			Log.e("parseDateToString", "转换失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将时间转换为中文
	 * 
	 * @param datetime
	 * @return
	 */
	public static String parseDateToChinese(Date datetime) {
		Date today = new Date();
		long seconds = (today.getTime() - datetime.getTime()) / 1000;

		long year = seconds / (24 * 60 * 60 * 30 * 12);// 相差年数
		long month = seconds / (24 * 60 * 60 * 30);// 相差月数
		long date = seconds / (24 * 60 * 60); // 相差的天数
		long hour = (seconds - date * 24 * 60 * 60) / (60 * 60);// 相差的小时数
		long minute = (seconds - date * 24 * 60 * 60 - hour * 60 * 60) / (60);// 相差的分钟数
		long second = (seconds - date * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);// 相差的秒数

		if (year > 0) {
			return year + "年前";
		}
		if (month > 0) {
			return month + "月前";
		}
		if (date > 0) {
			return date + "天前";
		}
		if (hour > 0) {
			return hour + "小时前";
		}
		if (minute > 0) {
			return minute + "分钟前";
		}
		if (second > 0) {
			return second + "秒前";
		}
		return parseDateToString(datetime);
	}

	/**
	 * 使用JavaMail发送邮件
	 * 
	 * @param content
	 */
	public static void sendEmailByJavaMail(String content) {
		try {
			// 设置邮件
			MailSenderInfo mailSenderInfo = new MailSenderInfo();
			mailSenderInfo.setMailServerHost(Config.MAIL_SERVER_HOST);
			mailSenderInfo.setMailServerPort(Config.MAIL_SERVER_PORT);
			mailSenderInfo.setValidate(true);
			mailSenderInfo.setUserName(Config.MAIL_ACCUNT); // 你的邮箱地址
			mailSenderInfo.setPassword(Config.MAIL_PASSWORD);// 您的邮箱密码
			mailSenderInfo.setFromAddress(Config.MAIL_ACCUNT);
			mailSenderInfo.setToAddress(Config.AUTHOR_EMAIL);
			mailSenderInfo.setSubject(Config.MAIL_SUBJECT);
			mailSenderInfo.setContent(content);

			// 发送邮件
			SimpleMailSender simpleMailSender = new SimpleMailSender();
			simpleMailSender.sendTextMail(mailSenderInfo);
			Log.i("sendEmailByJavaMail", "发送成功");
		} catch (Exception e) {
			Log.e("sendEmailByJavaMail", "发送失败");
			e.printStackTrace();
		}
	}

	/**
	 * 通过URL获取XML数据流
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream getXmlStreamByUrl(String path) {
		InputStream inputStream = null;
		Log.i("getXmlStreamByUrl", "获取XML InputStream    " + path);
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setConnectTimeout(3 * 1000);
			connection.setRequestMethod("GET");
			connection.connect();
			int code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
			}

			if (inputStream != null) {
				Log.i("getXmlStreamByUrl", "获取成功");
			}

		} catch (Exception e) {
			Log.e("getXmlStreamByUrl", "获取失败");
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 获取博客列表
	 * 
	 * @param path
	 * @return
	 */
	public static List<Blog> getBlogList(String path) {
		Log.i("getBlogList", "获取博客列表 XML" + path);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			BlogListHandler handler = new BlogListHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStreamByUrl(path);
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

	/**
	 * 获取博客内容
	 * 
	 * @param path
	 * @return
	 */
	public static String getBlogContent(String path) {
		String blogContent = "";
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			BlogItemHandler handler = new BlogItemHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStreamByUrl(path);
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

	/**
	 * 过滤xml特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceXmlTag(String str) {
//		str = str.replace("<p>", "\t\t");
		str = str.replace("</p>", "\r\n");
		str = str.replace("<br />", "\n");
		str = str.replace("<br/>", "\n");

		Pattern pattern = Pattern.compile("<img.+?>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		str = matcher.replaceAll("****图片暂时无法显示****");

		pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
		matcher = pattern.matcher(str);
		str = matcher.replaceAll("");

		str = str.replace("&nbsp;&nbsp;", "\t");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&#39;", "\\");
		str = str.replace("&quot;", "\\");
		str = str.replace("&gt;", ">");
		str = str.replace("&lt;", "<");
		str = str.replace("&amp;", "&");

		return str;
	}

	public static List<User> getUserList(String path) {
		Log.i("getBlogList", "获取博客列表 XML" + path);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			UserListHandler handler = new UserListHandler();
			reader.setContentHandler(handler);
			InputStream inputStream = getXmlStreamByUrl(path);
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

}

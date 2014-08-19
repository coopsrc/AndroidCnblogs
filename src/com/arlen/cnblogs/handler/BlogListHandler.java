package com.arlen.cnblogs.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.utils.AppUtils;

public class BlogListHandler extends DefaultHandler {

	private final String ENTRY_TAG = "entry"; // 主标记
	private final String ID_TAG = "id"; // 博客编号
	private final String TITLE_TAG = "title"; // 博客标题
	private final String SUMMARY_TAG = "summary"; // 博客摘要
	private final String PUBLISHED_TAG = "published"; // 发表时间
	private final String UPDATED_TAG = "updated"; // 更新时间
	private final String AUTHOR_NAME_TAG = "name"; // 作者名称
	private final String AUTHOR_URI_TAG = "uri"; // 作者主页
	private final String AUTHOR_AVATAR_TAG = "avatar"; // 作者头像
	private final String LINK_TAG = "link"; // 博客链接
	private final String LINK_HREF_TAG = "href"; // 博客链接地址
	private final String BLOGAPP_TAG = "blogapp"; // 作者用户名
	private final String DIGGS_TAG = "diggs"; // 推荐次数
	private final String VIEWS_TAG = "views"; // 浏览次数
	private final String COMMENTS_TAG = "comments"; // 评论次数

	private List<Blog> blogList;
	private Blog blogEntry;
	private boolean isStartParse;
	private StringBuilder currentData;

	public BlogListHandler() {
		super();
	}

	public BlogListHandler(List<Blog> blogList) {
		this.blogList = blogList;
	}

	public List<Blog> getBlogList() {
		return blogList;
	}

	/**
	 * 开始解析文档
	 */
	@Override
	public void startDocument() throws SAXException {
		Log.i("HomeListHandler", "开始解析文档");
		super.startDocument();
		blogList = new ArrayList<Blog>();
		currentData = new StringBuilder();
	}

	/**
	 * 文档结束
	 */
	@Override
	public void endDocument() throws SAXException {
		Log.i("HomeListHandler", "文档解析完成");
		super.endDocument();
	}

	/**
	 * 元素开始
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		Log.i("HomeListHandler", "startElement " + localName);
		if (localName.equalsIgnoreCase(ENTRY_TAG)) {
			blogEntry = new Blog();
			isStartParse = true;
			currentData.setLength(0);
		} else if (isStartParse && localName.equalsIgnoreCase(LINK_TAG)) {
			String path = attributes.getValue(LINK_HREF_TAG);
			if (!path.isEmpty()) {
				blogEntry.setBlogLink(AppUtils.parseStringToUrl(path));
			}
		}
	}

	/**
	 * 元素结束
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (isStartParse) {
			String chars = currentData.toString();
			Log.i("HomeListHandler", "正在解析:" + localName + " ---> " + chars);

			if (localName.equalsIgnoreCase(ID_TAG)) {
				int id = Integer.parseInt(chars);
				blogEntry.setBlogId(id);
			} else if (localName.equalsIgnoreCase(TITLE_TAG)) {
				chars = StringEscapeUtils.unescapeHtml(chars);
				blogEntry.setBlogTitle(chars);
			} else if (localName.equalsIgnoreCase(SUMMARY_TAG)) {
				chars = StringEscapeUtils.unescapeHtml(chars);
				blogEntry.setBlogSummary(chars);
			} else if (localName.equalsIgnoreCase(PUBLISHED_TAG)) {
				String dateString = chars.split("T")[0];
				Log.i("endElement", "dateString" + dateString);
				String timeString = chars.split("T")[1].split("\\+")[0];
				Log.i("endElement", "timeString" + timeString);
				String dateTimeString = dateString + " " + timeString;
				Log.i("endElement", "dateTimeString" + dateTimeString);
				Date publisheDate = AppUtils.parseStringToDate(dateTimeString);
				blogEntry.setPublishedDate(publisheDate);
			} else if (localName.equalsIgnoreCase(UPDATED_TAG)) {
				String dateString = chars.split("T")[0];
				String timeString = chars.split("T")[1].split("Z")[0];
				String dateTimeString = dateString + " " + timeString;
				Date updatedDate = AppUtils.parseStringToDate(dateTimeString);
				blogEntry.setUpdatedDate(updatedDate);
			} else if (localName.equalsIgnoreCase(AUTHOR_NAME_TAG)) {
				blogEntry.setAuthorName(chars);
			} else if (localName.equalsIgnoreCase(AUTHOR_URI_TAG)) {
				if (!chars.isEmpty()) {
					URL authorUri = AppUtils.parseStringToUrl(chars);
					blogEntry.setAuthorUri(authorUri);
				}
			} else if (localName.equalsIgnoreCase(AUTHOR_AVATAR_TAG)) {
				if (!chars.isEmpty()) {
					URL authorAvatar = AppUtils.parseStringToUrl(chars);
					blogEntry.setAuthorAvatar(authorAvatar);
				}
			} else if (localName.equalsIgnoreCase(LINK_TAG)) {

			} else if (localName.equalsIgnoreCase(LINK_HREF_TAG)) {

			} else if (localName.equalsIgnoreCase(BLOGAPP_TAG)) {
				blogEntry.setBlogApp(chars);
			} else if (localName.equalsIgnoreCase(DIGGS_TAG)) {
				int blogDiggs = Integer.parseInt(chars);
				blogEntry.setBlogDiggs(blogDiggs);
			} else if (localName.equalsIgnoreCase(VIEWS_TAG)) {
				int blogViews = Integer.parseInt(chars);
				blogEntry.setBlogViews(blogViews);
			} else if (localName.equalsIgnoreCase(COMMENTS_TAG)) {
				int blogComments = Integer.parseInt(chars);
				blogEntry.setBlogComments(blogComments);
			} else if (localName.equalsIgnoreCase(ENTRY_TAG)) {
				blogList.add(blogEntry);
				isStartParse = false;
			}
			currentData.setLength(0);
		}
	}

	/**
	 * 读取元素内容
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		currentData.append(ch, start, length);
		Log.i("endElement", "currentDate  ----> " + currentData);
	}

}

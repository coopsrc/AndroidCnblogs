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
import com.arlen.cnblogs.entity.News;
import com.arlen.cnblogs.utils.AppUtils;

public class NewsListHandler extends DefaultHandler {

	private final String ENTRY_TAG = "entry"; // 主标记
	private final String ID_TAG = "id"; // 博客编号
	private final String TITLE_TAG = "title"; // 博客标题
	private final String SUMMARY_TAG = "summary"; // 博客摘要
	private final String PUBLISHED_TAG = "published"; // 发表时间
	private final String UPDATED_TAG = "updated"; // 更新时间
	private final String LINK_TAG = "link"; // 博客链接
	private final String LINK_HREF_TAG = "href"; // 博客链接地址
	private final String DIGGS_TAG = "diggs"; // 推荐次数
	private final String VIEWS_TAG = "views"; // 浏览次数
	private final String COMMENTS_TAG = "comments"; // 评论次数
	private final String TOPIC_TAG = "topic"; // 评论次数
	private final String TOPI_ICON_TAG = "topicIcon"; // 评论次数
	private final String SOURCE_NAME_TAG = "sourceName"; // 评论次数

	private List<News> newsList;
	private News newsEntry;
	private boolean isStartParse;
	private StringBuilder currentData;

	public NewsListHandler() {
		super();
	}

	public NewsListHandler(List<News> newsList) {
		this.newsList = newsList;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	/**
	 * 开始解析文档
	 */
	@Override
	public void startDocument() throws SAXException {
		Log.i("HomeListHandler", "开始解析文档");
		super.startDocument();
		newsList = new ArrayList<News>();
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
			newsEntry = new News();
			isStartParse = true;
			currentData.setLength(0);
		} else if (isStartParse && localName.equalsIgnoreCase(LINK_TAG)) {
			String path = attributes.getValue(LINK_HREF_TAG);
			if (!path.isEmpty()) {
				newsEntry.setNewsLink(AppUtils.parseStringToUrl(path));
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
				newsEntry.setNewsId(id);
			} else if (localName.equalsIgnoreCase(TITLE_TAG)) {
				chars = StringEscapeUtils.unescapeHtml(chars);
				newsEntry.setNewsTitle(chars);
			} else if (localName.equalsIgnoreCase(SUMMARY_TAG)) {
				chars = StringEscapeUtils.unescapeHtml(chars);
				newsEntry.setNewsSummary(chars);
			} else if (localName.equalsIgnoreCase(PUBLISHED_TAG)) {
				String dateTimeString = chars.split("T")[0] + " "
						+ chars.split("T")[1].split("\\+")[0];
				Date publisheDate = AppUtils.parseStringToDate(dateTimeString);
				newsEntry.setPublishedDate(publisheDate);
			} else if (localName.equalsIgnoreCase(UPDATED_TAG)) {
				String dateTimeString = chars.split("T")[0] + " "
						+ chars.split("T")[1].split("Z")[0];
				Date updatedDate = AppUtils.parseStringToDate(dateTimeString);
				newsEntry.setUpdatedDate(updatedDate);
			} else if (localName.equalsIgnoreCase(DIGGS_TAG)) {
				int newsDiggs = Integer.parseInt(chars);
				newsEntry.setNewsDiggs(newsDiggs);
			} else if (localName.equalsIgnoreCase(VIEWS_TAG)) {
				int newsDiggs = Integer.parseInt(chars);
				newsEntry.setNewsViews(newsDiggs);
			} else if (localName.equalsIgnoreCase(COMMENTS_TAG)) {
				int blogComments = Integer.parseInt(chars);
				newsEntry.setNewsComments(blogComments);
			} else if (localName.equalsIgnoreCase(TOPIC_TAG)) {
				newsEntry.setNewsTopic(chars);
			} else if (localName.equalsIgnoreCase(TOPI_ICON_TAG)) {
				if (!chars.isEmpty()) {
					URL topicIcon = AppUtils.parseStringToUrl(chars);
					newsEntry.setTopicIcon(topicIcon);
				}
			} else if (localName.equalsIgnoreCase(SOURCE_NAME_TAG)) {
				newsEntry.setSourceName(chars);
			} else if (localName.equalsIgnoreCase(ENTRY_TAG)) {
				newsList.add(newsEntry);
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

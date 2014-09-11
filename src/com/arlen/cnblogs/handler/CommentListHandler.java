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

import com.arlen.cnblogs.entity.Comment;
import com.arlen.cnblogs.utils.AppUtils;

public class CommentListHandler extends DefaultHandler {

	private final String ENTRY_TAG = "entry"; // 主标记
	private final String ID_TAG = "id"; // 博客编号
	private final String TITLE_TAG = "title"; // 博客标题
	private final String PUBLISHED_TAG = "published"; // 发表时间
	private final String UPDATED_TAG = "updated"; // 更新时间
	private final String AUTHOR_NAME_TAG = "name"; // 作者名称
	private final String AUTHOR_URI_TAG = "uri"; // 作者主页
	private final String COMMENTS_TAG = "content"; // 评论次数

	private List<Comment> commentList;
	private Comment commentEntry;
	private boolean isStartParse;
	private StringBuilder currentData;

	public CommentListHandler() {
		super();
	}

	public CommentListHandler(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	/**
	 * 开始解析文档
	 */
	@Override
	public void startDocument() throws SAXException {
		Log.i("HomeListHandler", "开始解析文档");
		super.startDocument();
		commentList = new ArrayList<Comment>();
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
			commentEntry = new Comment();
			isStartParse = true;
			currentData.setLength(0);
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
				commentEntry.setCommentId(id);
			} else if (localName.equalsIgnoreCase(TITLE_TAG)) {
				chars = StringEscapeUtils.unescapeHtml(chars);
				commentEntry.setCommentTitle(chars);
			} else if (localName.equalsIgnoreCase(PUBLISHED_TAG)) {
				String dateString = chars.split("T")[0];
				Log.i("endElement", "dateString" + dateString);
				String timeString = chars.split("T")[1].split("\\+")[0];
				Log.i("endElement", "timeString" + timeString);
				String dateTimeString = dateString + " " + timeString;
				Log.i("endElement", "dateTimeString" + dateTimeString);
				Date publishedDate = AppUtils.parseStringToDate(dateTimeString);
				commentEntry.setPublishedDate(publishedDate);
			} else if (localName.equalsIgnoreCase(UPDATED_TAG)) {
				String dateString = chars.split("T")[0];
				String timeString = chars.split("T")[1].split("Z")[0];
				String dateTimeString = dateString + " " + timeString;
				Date updatedDate = AppUtils.parseStringToDate(dateTimeString);
				commentEntry.setUpdatedDate(updatedDate);
			} else if (localName.equalsIgnoreCase(AUTHOR_NAME_TAG)) {
				commentEntry.setAuthorName(chars);
			} else if (localName.equalsIgnoreCase(AUTHOR_URI_TAG)) {
				if (!chars.isEmpty()) {
					URL authorUri = AppUtils.parseStringToUrl(chars);
					commentEntry.setAuthorUri(authorUri);
				}
			} else if (localName.equalsIgnoreCase(COMMENTS_TAG)) {
				String commentContent = StringEscapeUtils.unescapeHtml(chars);
				commentEntry.setCommentContent(commentContent);
			} else if (localName.equalsIgnoreCase(ENTRY_TAG)) {
				commentList.add(commentEntry);
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

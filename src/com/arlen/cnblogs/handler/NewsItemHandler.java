package com.arlen.cnblogs.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class NewsItemHandler extends DefaultHandler {
	private final String CONTENT_TAG = "Content";

	private String newsContent;
	private boolean isStartParse;
	private StringBuilder currentData;

	public NewsItemHandler() {
		super();
	}

	public NewsItemHandler(String blogContent) {
		super();
		this.newsContent = blogContent;
	}

	public String getNewsContent() {
		return newsContent;
	}

	@Override
	public void startDocument() throws SAXException {
		Log.i("HomeItemHandler", "文档解析开始");
		super.startDocument();
		currentData = new StringBuilder();
	}

	@Override
	public void endDocument() throws SAXException {
		Log.i("HomeItemHandler", "文档解析结束");
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase(CONTENT_TAG)) {
			newsContent = "";
			isStartParse = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (isStartParse) {// 发现目标
			String chars = currentData.toString();
			Log.i("Blog", "正在解析" + localName);
			// 处理
			if (localName.equalsIgnoreCase(CONTENT_TAG)) {// 标题
				newsContent = chars;
				isStartParse = false;
			}
			currentData.setLength(0);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		currentData.append(ch, start, length);
	}

}

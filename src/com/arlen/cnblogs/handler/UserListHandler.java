package com.arlen.cnblogs.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.utils.AppUtils;

public class UserListHandler extends DefaultHandler {
	private final String ENTRY_TAG = "entry";
	private final String ID_TAG = "id";
	private final String tITLE_TAG = "title";
	private final String UPDATE_TAG = "updated";
	private final String LINK_TAG = "link";
	private final String LINK_HERF_TAG = "href";
	private final String BLOGAPP_TAG = "blogapp";
	private final String AVATAR_TAG = "avatar";
	private final String PASTCOUNT_TAG = "postcount";

	private List<User> userList;
	private User userEntry;
	private boolean isStartParse;
	private StringBuilder currentData;

	public UserListHandler() {
		super();
	}

	public UserListHandler(List<User> userList) {
		super();
		this.userList = userList;
	}

	public List<User> getUserList() {
		return userList;
	}

	@Override
	public void startDocument() throws SAXException {
		Log.i("UserListHandler", "开始解析文档");
		super.startDocument();
		userList = new ArrayList<User>();
		currentData = new StringBuilder();
	}

	@Override
	public void endDocument() throws SAXException {
		Log.i("UserListHandler", "文档解析完成");
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		Log.i("UserListHandler", "startElement " + localName);
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase(ENTRY_TAG)) {
			userEntry = new User();
			isStartParse = true;
			currentData.setLength(0);
		} else if (isStartParse && localName.equalsIgnoreCase(LINK_TAG)) {
			String path = attributes.getValue(LINK_HERF_TAG);
			if (!path.isEmpty()) {
				userEntry.setUserLink(AppUtils.parseStringToUrl(path));
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (isStartParse) {
			String chars = currentData.toString();
			Log.i("UserListHandler", "正在解析:" + localName + " ---> " + chars);
			if (localName.equalsIgnoreCase(ID_TAG)) {
				URL id = AppUtils.parseStringToUrl(chars);
				userEntry.setId(id);
			} else if (localName.equalsIgnoreCase(tITLE_TAG)) {
				userEntry.setTitle(chars);
			} else if (localName.equalsIgnoreCase(UPDATE_TAG)) {
				String dateString = chars.split("T")[0];
				Log.i("endElement", "dateString" + dateString);
				String timeString = chars.split("T")[1].split("\\+")[0];
				Log.i("endElement", "timeString" + timeString);
				String dateTimeString = dateString + " " + timeString;
				Log.i("endElement", "dateTimeString" + dateTimeString);
				Date updatedDate = AppUtils.parseStringToDate(dateTimeString);
				userEntry.setUpdatedDate(updatedDate);
			} else if (localName.equalsIgnoreCase(BLOGAPP_TAG)) {
				userEntry.setBlogapp(chars);
			} else if (localName.equalsIgnoreCase(AVATAR_TAG)) {
				URL userAvatar = AppUtils.parseStringToUrl(chars);
				userEntry.setUserAvatar(userAvatar);
			} else if (localName.equalsIgnoreCase(PASTCOUNT_TAG)) {
				int postCount = Integer.parseInt(chars);
				userEntry.setPostCount(postCount);
			} else if (localName.equalsIgnoreCase(ENTRY_TAG)) {
				userList.add(userEntry);
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
		Log.i("characters", "currentDate  ----> " + currentData);
	}

}

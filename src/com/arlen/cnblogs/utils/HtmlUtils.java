package com.arlen.cnblogs.utils;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arlen.cnblogs.bean.PersonInfo;
import com.arlen.cnblogs.bean.UserInfo;

public class HtmlUtils {

	public static Map<String, String> getNameParam() throws Exception {

		String path = AppMacros.CNBLOGS_LOGIN;
		Map<String, String> map = new HashMap<String, String>();

		Document document = Jsoup.connect(path).get();
		Elements contents = document.select("input");

		for (int i = 0; i < contents.size(); i++) {
			String name = contents.get(i).attr("name");
			String value = contents.get(i).attr("value");

			map.put(name, value);
			System.out.println("name:" + name + ",value:" + value);
		}

		System.out.println("map size:" + map.size());

		return map;
	}

	public static PersonInfo getPersonInfo(String blogApp) throws Exception {
		PersonInfo personInfo = new PersonInfo();
		String path = AppMacros.PERSON_INFO + blogApp;

		Document bodyDocument = Jsoup.connect(path).get();
		Element divElement = bodyDocument.getElementById("profile_block");
		Elements aElements = divElement.select("a");

		for (int i = 0; i < aElements.size(); i++) {
			System.out.println(aElements.get(i).ownText());
			System.out.println("====================");

			switch (i) {
			case 0:
				personInfo.setNickName(aElements.get(i).ownText());
				break;
			case 1:
				personInfo.setCnblogsAge(aElements.get(i).ownText());
				break;
			case 2:
				personInfo.setFollowers(Integer.parseInt(aElements.get(i)
						.ownText()));
				break;
			case 3:
				personInfo.setFollowees(Integer.parseInt(aElements.get(i)
						.ownText()));
				break;
			}
		}

		return personInfo;
	}

	public static String getBlogApp(String html) throws Exception {
		Document bodyDocument = Jsoup.parse(html);
		Element divElement = bodyDocument.getElementById("app_list_wrapper");
		Elements ulElements = divElement.select("ul");
		Elements liElements = ulElements.select("li");
		Elements aElements = liElements.get(1).select("a");
		Element element = aElements.get(0);
		if(element.ownText().equals("Ð´²©")){
			String herf = element.attr("href");
			System.out.println(herf);
			System.out.println(herf.split("/")[3]);
			return herf.split("/")[3];
		}
		
		return null;
	}
	
	public static UserInfo getUserInfo() throws Exception {
		UserInfo userInfo = new UserInfo();
		String path = AppMacros.CURRENT_USER_INFO;

		Document document = Jsoup.connect(path).get();
		Element divElement = document.getElementById("header_user_right");
		Elements aElements = divElement.select("a");
		System.out.println("*******Current UserInfo************");
		System.out.println(aElements.get(0).attr("href").split("/")[1]);
		System.out.println(aElements.get(0).attr("src"));
		System.out.println(aElements.get(1).ownText());
		System.out.println(aElements.get(2).attr("href"));

		return userInfo;
	}

	public static UserInfo getUserInfo(String html) throws Exception {
		UserInfo userInfo = new UserInfo();

		Document document = Jsoup.parse(html);
		Element divElement = document.getElementById("header_user_right");
		Elements aElements = divElement.select("a");
		System.out.println("*******Current UserInfo************");
		System.out.println(aElements.get(0).attr("href").split("/")[1]);
		System.out.println(aElements.get(0).attr("src"));
		System.out.println(aElements.get(1).ownText());
		System.out.println(aElements.get(2).attr("href"));

		return userInfo;
	}
}

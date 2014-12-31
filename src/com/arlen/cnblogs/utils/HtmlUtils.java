package com.arlen.cnblogs.utils;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlUtils {

	public static Map<String, String> getNameParam()
			throws Exception {

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
}

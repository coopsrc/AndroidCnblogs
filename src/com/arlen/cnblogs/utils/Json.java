package com.arlen.cnblogs.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {

  // 解析单一的json封装,并返回字符串数组
  /**
   * 参数说明: 1.webContent 获取的网页封装的json格式数据 2.key 以数组形式组成的json的键名称
   * */
  public String[] getJSON(String webContent, String[] key) {
    int size = key.length;
    String[] s = new String[size];
    try {
      JSONObject jsonObject = new JSONObject(webContent);
      for (int j = 0; j < size; j++) {
        s[j] = jsonObject.getString(key[j]);
        System.out.println(key[j] + "===string===" + jsonObject.getString(key[j]));
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      s = null;
    }
    return s;
  }

  // 获取数组型的结构,返回ArrayList<HashMap<String, Object>>,方便listview中填充数据
  /**
   * 参数说明: 1.webContent 获取的网页封装的json格式数据 2.key 以数组形式组成的json的键名称 3.jsonName 封装json数组数据的json名称
   * */

  public ArrayList<HashMap<String, Object>> getJSONArray(String webContent, String[] key,
      String jsonName) {
    ArrayList<HashMap<String, Object>> list;
    JSONArray jsonObject;
    try {
      jsonObject = new JSONObject(webContent).getJSONArray(jsonName);
      list = new ArrayList<HashMap<String, Object>>();
      for (int i = 0; i < jsonObject.length(); i++) {
        JSONObject jsonObject2 = (JSONObject) jsonObject.opt(i);
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int j = 0; j < key.length; j++) {
          map.put(key[j], jsonObject2.getString(key[j]));
          System.out.println(key[j] + "===" + jsonObject2.getString(key[j]));
        }
        list.add(map);
      }

    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      list = null;
    }
    return list;
  }
}

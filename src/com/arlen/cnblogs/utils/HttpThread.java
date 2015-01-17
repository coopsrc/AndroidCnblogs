package com.arlen.cnblogs.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class HttpThread extends Thread {

  private Context context;
  private ProgressDialog proDialog;
  private String url;
  private String[] key;
  private String[] value;
  private String[] jsonKey;
  private String jsonName;


  private String[] array;
  ArrayList<HashMap<String, Object>> list;

  public HttpThread(Context context, ProgressDialog proDialog) {
    this.context = context;
    this.proDialog = proDialog;
  }

  @Override
  public void run() {
    Message msg = handler.obtainMessage();
    HttpPostRequest post = new HttpPostRequest();
    int res = post.requestHttp(url, key, value);
    String webContent = post.getWebContext();
    msg.what = res;
    if (res == 1) {
      // 解析json
      Json json = new Json();
      if (jsonName != null)
        // 解析数组型的json
        list = json.getJSONArray(webContent, jsonKey, jsonName);
      else
        // 解析单个json值
        array = json.getJSON(webContent, jsonKey);
    }
    handler.sendMessage(msg);
  }

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      int what = msg.what;
      HttpStatus status = new HttpStatus();
      // 返回成功数据时
      if (what == 1) {
        // 处理数组型json
        if (list != null) {
          int size = list.size();
          String result = "";
          for (int i = 0; i < size; i++) {
            int s = jsonKey.length;
            // 迭代jsonKey数组值
            for (int j = 0; j < s; j++) {
              result += jsonKey[j] + ":" + list.get(i).get(jsonKey[j]) + "\n";
            }
            result += "\n";
          }
          Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
        // 处理单个字符json
        if (array != null) {
          int arraySize = array.length;
          String rs = "";
          for (int k = 0; k < arraySize; k++) {
            rs += jsonKey[k] + ":" + array[k] + "\n";
          }
          Toast.makeText(context, rs, Toast.LENGTH_LONG).show();
        }
      }
      // 根据服务器端返回数据,自定义提示
      else if (what == 2) {
        status.setTips("自定义提示2");
      }// 根据服务器端返回数据,自定义提示
      else if (what == 3) {
        status.setTips("自定义提示3");
      }
      status.ShowHttpStatusTips(what, context, proDialog);
    }
  };

  // activity界面传递的参数
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String[] getKey() {
    return key;
  }

  public void setKey(String[] key) {
    this.key = key;
  }

  public String[] getValue() {
    return value;
  }

  public void setValue(String[] value) {
    this.value = value;
  }

  public String[] getJsonKey() {
    return jsonKey;
  }

  public void setJsonKey(String[] jsonKey) {
    this.jsonKey = jsonKey;
  }

  public String getJsonName() {
    return jsonName;
  }

  public void setJsonName(String jsonName) {
    this.jsonName = jsonName;
  }
}

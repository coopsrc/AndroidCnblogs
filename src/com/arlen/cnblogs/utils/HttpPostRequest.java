package com.arlen.cnblogs.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



public class HttpPostRequest {

  private String webContext;

  // 返回请求内容
  public String getWebContext() {
    return webContext;
  }

  public void setWebContext(String webContext) {
    this.webContext = webContext;
  }

  // 该函数返回服务器访问的各种状态,并通过webContext传递获取的文本值
  /**
   * 参数说明 url 访问的网络地址 key 传递参数的名称 value 传递参数的值 key 与value数组长度对应,即一对键值对,这样可以不限制参数传递的个数
   * */
  public int requestHttp(String url, String[] key, String[] value) {
    // TODO Auto-generated method stub
    int status = 0;
    DefaultHttpClient mHttpClient = new DefaultHttpClient();
    HttpPost mPost = new HttpPost(url);
    List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
    int size = key.length;
    for (int i = 0; i < size; i++) {
      pairs.add(new BasicNameValuePair(key[i], value[i]));
    }
    try {
      mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
    } catch (UnsupportedEncodingException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    try {
      mHttpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 60000); // Socket超时设置60s
      mHttpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 60000);// 连接超时60s
      HttpResponse response = mHttpClient.execute(mPost);
      int res = response.getStatusLine().getStatusCode();
      if (res == 200) {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          String info = EntityUtils.toString(entity);
          setWebContext(info);
          status = 1;
        }
      } else if (res == 404) {
        status = 404;
      } else if (res == 500) {
        status = 500;
      }
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      status = 900;
    } catch (ConnectTimeoutException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      status = 901;
    } catch (InterruptedIOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      status = 902;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      status = 903;
    }
    return status;
  }
}

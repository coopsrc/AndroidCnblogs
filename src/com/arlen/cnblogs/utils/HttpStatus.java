package com.arlen.cnblogs.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class HttpStatus {
  // 服务器端返回的状态提示
  public static final String HTTP_0 = "其他错误";
  public static final String HTTP_1 = "";
  public static final String HTTP_404 = "404错误,请求链接无效";
  public static final String HTTP_500 = "网络500错误,服务器端程序出错";
  public static final String HTTP_900 = "网络传输协议出错";
  public static final String HTTP_901 = "连接超时";
  public static final String HTTP_902 = "网络中断";
  public static final String HTTP_903 = "网络数据流传输出错";
  public static final String HTTP_UNKONW = "未知的错误";

  // 自定义的提示
  private String tips;

  public void ShowHttpStatusTips(int status, Context context, ProgressDialog proDialog) {
    switch (status) {
      case 0:
        Toast.makeText(context, getTips(), Toast.LENGTH_LONG).show();
        break;
      case 1:
        // Toast.makeText(context, getTips(), Toast.LENGTH_LONG).show();
        break;
      case 2:
        Toast.makeText(context, getTips(), Toast.LENGTH_LONG).show();
        break;
      case 3:
        Toast.makeText(context, getTips(), Toast.LENGTH_LONG).show();
        break;
      case 404:
        Toast.makeText(context, HTTP_404, Toast.LENGTH_LONG).show();

        break;
      case 500:
        Toast.makeText(context, HTTP_500, Toast.LENGTH_LONG).show();
        break;
      case 900:
        Toast.makeText(context, HTTP_900, Toast.LENGTH_LONG).show();
        break;
      case 901:
        Toast.makeText(context, HTTP_901, Toast.LENGTH_LONG).show();
        break;
      case 902:
        Toast.makeText(context, HTTP_902, Toast.LENGTH_LONG).show();
        break;
      case 903:
        Toast.makeText(context, HTTP_903, Toast.LENGTH_LONG).show();
        break;
      default:
        Toast.makeText(context, HTTP_UNKONW, Toast.LENGTH_LONG).show();
        break;
    }
    if (proDialog != null) proDialog.dismiss();
  }

  public String getTips() {
    return tips;
  }

  public void setTips(String tips) {
    this.tips = tips;
  }
}

package com.arlen.cnblogs.login;

import java.util.List;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.content.Context;

import com.arlen.cnblogs.utils.DBUtils;

public class Personal {
	public static String Login(String userName, String password, String loginUrl) {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		PostMethod postMethod = new PostMethod(loginUrl);

		NameValuePair[] postData = { new NameValuePair("tbUserName", userName),
				new NameValuePair("tbPassword", password) };
		postMethod.setRequestBody(postData);

		try {

			httpClient.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(postMethod);
			Cookie[] cookies = httpClient.getState().getCookies();
			StringBuffer stringBuffer = new StringBuffer();
			for (Cookie c : cookies) {
				stringBuffer.append(c.toString() + ";");
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isLogin(Context context) {
		boolean flag = false;
		List<String> cookieList = DBUtils.listCookie(context, null);

		if (cookieList.size() != 0) {
			flag = true;
		}

		return flag;
	}

	public static boolean isLogin(Context context, String userName) {
		boolean flag = false;
		String[] selectionArgs = { userName };
		String cookie = DBUtils.viewCookie(context, selectionArgs);

		if (!cookie.equals("")) {
			flag = true;
		}

		return flag;
	}
}

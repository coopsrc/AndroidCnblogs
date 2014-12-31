package com.arlen.cnblogs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;

import android.net.http.AndroidHttpClient;
import android.util.Log;

public class LoginUtils {

	private static final String TAG = LoginUtils.class.getSimpleName();

	private static AndroidHttpClient httpClient = AndroidHttpClient
			.newInstance("");
	private static BasicHttpContext httpContext = new BasicHttpContext();
	private static BasicCookieStore cookieStore = new BasicCookieStore();

	public static void login(String userName, String password,
			Map<String, String> map) throws Exception {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		HttpPost httpPost = new HttpPost(AppMacros.CNBLOGS_LOGIN);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", map.get("__VIEWSTATE")));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", map
				.get("__VIEWSTATEGENERATOR")));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", map
				.get("__EVENTVALIDATION")));
		params.add(new BasicNameValuePair("tbUserName", userName));
		params.add(new BasicNameValuePair("tbPassword", password));
		params.add(new BasicNameValuePair("chkRemember", "on"));
		params.add(new BasicNameValuePair("btnLogin", map.get("btnLogin")));
		params.add(new BasicNameValuePair("txtReturnUrl", map
				.get("txtReturnUrl")));

		httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Origin", "http://passport.cnblogs.com");
		httpPost.addHeader("Referer", "http://passport.cnblogs.com/login.aspx");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		printCookies(httpPost.getHeaders("Cookie"));

		try {
			HttpResponse httpResponse = httpClient.execute(httpPost,
					httpContext);

			Log.i(TAG, "=======" + httpResponse.getAllHeaders().length);

			Header locationHeader = httpResponse.getFirstHeader("Location");

			if (locationHeader != null) {
				Log.i(TAG, "µÇÂ½³É¹¦£º" + locationHeader.getValue());
			} else {
				Log.i(TAG, "µÇÂ¼Ê§°Ü£¡£¡£¡£¡£¡");
			}
			printCookies(httpPost.getHeaders("Cookie"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void login(String userName, String password, String code,
			Map<String, String> map) throws Exception {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		HttpPost httpPost = new HttpPost(AppMacros.CNBLOGS_LOGIN);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", map.get("__VIEWSTATE")));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", map
				.get("__VIEWSTATEGENERATOR")));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", map
				.get("__EVENTVALIDATION")));
		params.add(new BasicNameValuePair("tbUserName", userName));
		params.add(new BasicNameValuePair("tbPassword", password));
		params.add(new BasicNameValuePair("LBD_VCID_c_login_logincaptcha", map
				.get("LBD_VCID_c_login_logincaptcha")));
		params.add(new BasicNameValuePair(
				"LBD_BackWorkaround_c_login_logincaptcha", map
						.get("LBD_BackWorkaround_c_login_logincaptcha")));
		params.add(new BasicNameValuePair("CaptchaCodeTextBox", code));
		params.add(new BasicNameValuePair("chkRemember", "on"));
		params.add(new BasicNameValuePair("btnLogin", map.get("btnLogin")));
		params.add(new BasicNameValuePair("txtReturnUrl", map
				.get("txtReturnUrl")));

		httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Origin", "http://passport.cnblogs.com");
		httpPost.addHeader("Referer", "http://passport.cnblogs.com/login.aspx");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		try {
			HttpResponse httpResponse = httpClient.execute(httpPost,
					httpContext);

			Log.i(TAG, "=======" + httpResponse.getAllHeaders().length);

			Header locationHeader = httpResponse.getFirstHeader("Location");

			if (locationHeader != null) {
				Log.i(TAG, "µÇÂ½³É¹¦£º" + locationHeader.getValue());
			} else {
				Log.i(TAG, "µÇÂ¼Ê§°Ü£¡£¡£¡£¡£¡");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void printCookies(Header[] headers) {
		System.out.println("======Cookies=======");
		for (int i = 0; i < headers.length; i++) {
			System.out.println("==" + headers[i].getValue());
		}
		System.out.println("=============");
	}
}

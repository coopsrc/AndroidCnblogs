package com.arlen.cnblogs.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.arlen.cnblogs.bean.LoginSid;

public class UserUtils {
	private static final String TAG = UserUtils.class.getSimpleName();
	public static String cookie = "";

	public static void Login(String userName, String password) throws Exception {

		String loginUrl = AppMacros.CNBLOGS_LOGIN;
		String returnUrl = AppMacros.CNBLOGS_HOME;
		LoginSid loginSid = new LoginSid();

		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();

		HttpPost httpPost = new HttpPost(loginUrl);
		httpPost.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpPost.addHeader("Connection", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpPost.addHeader("Content-Type", "keep-alive");
		httpPost.addHeader("Origin", "http://passport.cnblogs.com");
		httpPost.addHeader("Referer", "http://passport.cnblogs.com/login.aspx");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__EVENTARGUMENT", loginSid
				.getEventArgument()));
		params.add(new BasicNameValuePair("__EVENTTARGET", loginSid
				.getEventTarget()));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", loginSid
				.getEventValidation()));
		params.add(new BasicNameValuePair("__VIEWSTATE", loginSid
				.getViewState()));
		params.add(new BasicNameValuePair("btnLogin", "µÇ  Â¼"));
		params.add(new BasicNameValuePair("chkRemember", "on"));
		params.add(new BasicNameValuePair("tbPassword", password));
		params.add(new BasicNameValuePair("tbUserName", userName));
		params.add(new BasicNameValuePair("txtReturnUrl", returnUrl));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		CloseableHttpResponse httpResponse = httpclient.execute(httpPost);

		Header locationHeader = httpResponse.getFirstHeader("Location");

		if (locationHeader == null) {
			Log.e(TAG, "µÇÂ¼Ê§°Ü");
		} else {
			Log.i(TAG, "µÇÂ¼³É¹¦");
			List<Cookie> cookies = cookieStore.getCookies();
			if (cookies.isEmpty()) {
				Log.i(TAG, "CookieÎª¿Õ");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					if (cookies.get(i).getName().endsWith("SERVERID")) {
						cookie = "SERVERID=" + cookies.get(i).getValue();
					}
				}
			}
		}
	}

	public static void Login(String userName, String password, String code)
			throws Exception {

		String loginUrl = AppMacros.CNBLOGS_LOGIN;
		String returnUrl = AppMacros.CNBLOGS_HOME;
		LoginSid loginSid = new LoginSid();

		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();

		HttpPost httpPost = new HttpPost(loginUrl);
		httpPost.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpPost.addHeader("Connection", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpPost.addHeader("Content-Type", "keep-alive");
		httpPost.addHeader("Origin", "http://passport.cnblogs.com");
		httpPost.addHeader("Referer", "http://passport.cnblogs.com/login.aspx");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__EVENTARGUMENT", loginSid
				.getEventArgument()));
		params.add(new BasicNameValuePair("__EVENTTARGET", loginSid
				.getEventTarget()));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", loginSid
				.getEventValidation()));
		params.add(new BasicNameValuePair("__VIEWSTATE", loginSid
				.getViewState()));
		params.add(new BasicNameValuePair("btnLogin", "µÇ  Â¼"));
		params.add(new BasicNameValuePair("CaptchaCodeTextBox", code));
		params.add(new BasicNameValuePair("chkRemember", "on"));
		params.add(new BasicNameValuePair(
				"LBD_BackWorkaround_c_login_logincaptcha", loginSid
						.getLoginCaptchaB()));
		params.add(new BasicNameValuePair("LBD_VCID_c_login_logincaptcha",
				loginSid.getLoginCaptchaV()));
		params.add(new BasicNameValuePair("tbPassword", password));
		params.add(new BasicNameValuePair("tbUserName", userName));
		params.add(new BasicNameValuePair("txtReturnUrl", returnUrl));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		CloseableHttpResponse httpResponse = httpclient.execute(httpPost);

		Header locationHeader = httpResponse.getFirstHeader("Location");

		if (locationHeader == null) {
			Log.e(TAG, "µÇÂ¼Ê§°Ü");
		} else {
			Log.i(TAG, "µÇÂ¼³É¹¦");
			List<Cookie> cookies = cookieStore.getCookies();
			if (cookies.isEmpty()) {
				Log.i(TAG, "CookieÎª¿Õ");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					if (cookies.get(i).getName().endsWith("SERVERID")) {
						cookie = "SERVERID=" + cookies.get(i).getValue();
					}
				}
			}
		}
	}

}

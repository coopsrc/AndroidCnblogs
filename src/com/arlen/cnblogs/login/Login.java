package com.arlen.cnblogs.login;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Login {
	private static void HttpClientLogin(String userName, String password,
			String loginUrl, String dataUrl) {
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

			GetMethod getMethod = new GetMethod(dataUrl);
			getMethod.setRequestHeader("Cookie", stringBuffer.toString());
			postMethod.setRequestHeader("Host", "passport.cnblogs.com");
			postMethod.setRequestHeader("Referer", "http://home.cnblogs.com/");
			postMethod.setRequestHeader("User-Agent", "AndroidCnblogs");
			httpClient.executeMethod(getMethod);

			String result = getMethod.getResponseBodyAsString();
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.arlen.cnblogs.task;

import com.arlen.cnblogs.utils.HttpUtils;

import android.os.AsyncTask;
import android.webkit.WebView;

public class BlogContentTask extends AsyncTask<String, Void, Void> {

	private WebView webView;

	private String content;

	public BlogContentTask(WebView webView) {
		super();
		this.webView = webView;
	}

	@Override
	protected Void doInBackground(String... params) {
		content = HttpUtils.getBlogContent(params[0]);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8",
				null);
	}

}

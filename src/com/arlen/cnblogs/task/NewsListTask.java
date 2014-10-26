package com.arlen.cnblogs.task;

import java.util.ArrayList;
import java.util.List;

import com.arlen.cnblogs.adapter.NewsListAdapter;
import com.arlen.cnblogs.entity.News;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.HttpUtil;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

public class NewsListTask extends AsyncTask<String, Void, Void> {
	private List<News> newsList;
	private List<News> newList = new ArrayList<News>();
	private SwipeRefreshLayout swipeRefreshLayout;
	private NewsListAdapter adapter;

	public NewsListTask(List<News> newsList,
			SwipeRefreshLayout swipeRefreshLayout, NewsListAdapter adapter) {
		super();
		this.newsList = newsList;
		this.swipeRefreshLayout = swipeRefreshLayout;
		this.adapter = adapter;
	}

	protected Void doInBackground(String... params) {

		newList = HttpUtil.getNewsList(params[0]);

		if (params[1].equals("init")) {
			newsList.addAll(newList);
		} else if (params[1].equals("refresh")) {
			newList.addAll(newsList);
			AppUtils.removeDuplicate(newList);
			newsList.clear();
			newsList.addAll(newList);
		} else if (params[1].equals("loadMore")) {
			newsList.addAll(newList);
			AppUtils.removeDuplicate(newsList);
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		newList.clear();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		swipeRefreshLayout.setRefreshing(false);
		adapter.updataNewsList(newsList);
		adapter.notifyDataSetChanged();
	}

}

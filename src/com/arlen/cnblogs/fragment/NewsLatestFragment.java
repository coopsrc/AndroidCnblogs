package com.arlen.cnblogs.fragment;

import java.util.ArrayList;
import java.util.List;

import com.arlen.cnblogs.NewsActivity;
import com.arlen.cnblogs.R;
import com.arlen.cnblogs.adapter.NewsListAdapter;
import com.arlen.cnblogs.entity.News;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NewsLatestFragment extends ListFragment {
	
	private List<News> newsList;
	private String path;
	private int pageIndex;
	private int pageSize;

	private NewsListAdapter adapter;
	private Handler handler = null;

	private Intent intent;

	public NewsLatestFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news_latest,
				container, false);
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showBlogItem(newsList.get(position));
	}
	
	private void showBlogItem(News newsEntry) {
		intent = new Intent(this.getActivity(), NewsActivity.class);
		if (newsEntry.getTopicIcon() != null) {
			intent.putExtra("topicIcon", newsEntry.getTopicIcon().toString());
		} else {
			intent.putExtra("topicIcon",
					"https://github.com/ZhangTingkuo/AndroidCnblogs/blob/master/res/drawable-hdpi/ic_launcher.png");
		}

		intent.putExtra("title", newsEntry.getNewsTitle());
		intent.putExtra("sourceName", newsEntry.getSourceName());
		intent.putExtra("published",
				AppUtils.parseDateToString(newsEntry.getPublishedDate()));
		intent.putExtra("id", newsEntry.getNewsId());
		
		startActivity(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		Log.i("onCreateContextMenu", "000000000");
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = this.getActivity().getMenuInflater();
		inflater.inflate(R.menu.blog_list_context_menu, menu);
		menu.setHeaderTitle(R.string.app_name);
		Log.i("onCreateContextMenu", "1111111111");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		newsList = new ArrayList<News>();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					initData();
					handler.sendMessage(handler.obtainMessage(0, newsList));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		try {
			new Thread(runnable).start();
			handler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (msg.what == 0) {
						ArrayList<News> news = (ArrayList<News>) msg.obj;
						BindListData(news);
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initData() {
		newsList.clear();

		path = Config.RECENT_NEWS_PAGED;
		pageIndex = 1;
		pageSize = Config.News_PAGE_SIZE;
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);
		Log.i("HomeFragment", "最新新闻列表地址：" + path);
		Log.i("HomeFragment", "获取最新新闻列表  --->  开始");
		newsList = AppUtils.getNewsList(path);
		Log.i("HomeFragment", "获取最新新闻列表  --->  完成");
	}
	
	private void BindListData(ArrayList<News> news) {
		adapter = new NewsListAdapter(getActivity(), news);
		this.setListAdapter(adapter);
	}

}

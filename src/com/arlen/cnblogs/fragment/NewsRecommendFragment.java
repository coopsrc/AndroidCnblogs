package com.arlen.cnblogs.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.arlen.cnblogs.NewsActivity;
import com.arlen.cnblogs.R;
import com.arlen.cnblogs.adapter.NewsListAdapter;
import com.arlen.cnblogs.dialog.ItemDialog;
import com.arlen.cnblogs.entity.News;
import com.arlen.cnblogs.task.NewsListTask;
import com.arlen.cnblogs.utils.AppConfig;
import com.arlen.cnblogs.utils.AppUtils;

public class NewsRecommendFragment extends Fragment implements
		OnItemLongClickListener, OnItemClickListener, OnRefreshListener,
		OnScrollListener {
	private static final String TAG = NewsRecommendFragment.class
			.getSimpleName();

	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView listView;

	private NewsListAdapter adapter;

	private String path;
	private int pageSize;
	private int pageIndex = 1;
	private List<News> newsList;

	private int lastVisibleIndex;
	private int maxVisibleIndex = 400;

	private Intent intent;

	public NewsRecommendFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_news_recommend,
				container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.i(TAG, "onViewCreated");
		initComponent();
		initData();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(TAG, "onItemClick -- " + position);
		showNewsItem(newsList.get(position));
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Log.e("onItemLongClick", "position   ---   " + position);
		String[] items = getActivity().getResources().getStringArray(
				R.array.news_list_dialog);
		ItemDialog dialog = new ItemDialog(getActivity(), items);
		dialog.setTitle("新闻");

		dialog.TAG = "news";
		dialog.newsEntry = newsList.get(position);

		dialog.show();

		return true;
	}

	@Override
	public void onRefresh() {
		new NewsListTask(newsList, swipeRefreshLayout, adapter).execute(path,
				"refresh");
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (adapter.getCount() < maxVisibleIndex) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& lastVisibleIndex == adapter.getCount() - 1) {

				pageIndex++;
				initPath(pageIndex);
				swipeRefreshLayout.setRefreshing(true);
				new NewsListTask(newsList, swipeRefreshLayout, adapter)
						.execute(path, "loadMore");
			}
		} else {
			// Toast.makeText(getActivity(), "最后一页!",
			// Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
	}

	private void initComponent() {
		swipeRefreshLayout = (SwipeRefreshLayout) this.getActivity()
				.findViewById(R.id.swipeRefreshLayoutRecommendNews);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		listView = (ListView) this.getActivity().findViewById(
				R.id.listViewRecommendNews);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setOnScrollListener(this);
	}

	private void initData() {
		newsList = new ArrayList<News>();
		adapter = new NewsListAdapter(getActivity(), newsList);
		listView.setAdapter(adapter);

		initPath(1);
		swipeRefreshLayout.setRefreshing(true);
		new NewsListTask(newsList, swipeRefreshLayout, adapter).execute(path,
				"init");
	}

	private void initPath(int pageIndex) {
		// http://wcf.open.cnblogs.com/news/recommend/paged/{PAGEINDEX}/{PAGESIZE}
		path = AppConfig.RECOMMEND_NEWS_PAGED;
		pageSize = AppConfig.PAGE_SIZE;
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);

		Log.i(TAG, "pageIndex：" + pageIndex);
	}

	private void showNewsItem(News newsEntry) {
		intent = new Intent(this.getActivity(), NewsActivity.class);
		if (newsEntry.getTopicIcon() != null) {
			intent.putExtra("topicIcon", newsEntry.getTopicIcon().toString());
		} else {
			intent.putExtra("topicIcon", "");
		}

		intent.putExtra("title", newsEntry.getNewsTitle());
		intent.putExtra("sourceName", newsEntry.getSourceName());
		intent.putExtra("published",
				AppUtils.parseDateToString(newsEntry.getPublishedDate()));
		intent.putExtra("id", newsEntry.getNewsId());

		// for share
		intent.putExtra("link", newsEntry.getNewsLink().toString());

		startActivity(intent);
	}
}

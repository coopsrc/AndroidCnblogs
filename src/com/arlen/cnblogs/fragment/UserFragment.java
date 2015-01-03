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

import com.arlen.cnblogs.R;
import com.arlen.cnblogs.UserActivity;
import com.arlen.cnblogs.adapter.UserListAdapter;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.task.UserListTask;
import com.arlen.cnblogs.utils.AppMacros;

public class UserFragment extends Fragment implements OnItemLongClickListener,
		OnItemClickListener, OnRefreshListener, OnScrollListener {
	private static final String TAG = UserFragment.class.getSimpleName();

	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView listView;

	private UserListAdapter adapter;

	private String path;
	private int pageSize;
	private int pageIndex = 1;
	private List<User> userList;

	private int lastVisibleIndex;
	private int maxVisibleIndex = 400;

	private Intent intent;

	public UserFragment() {
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
		View rootView = inflater.inflate(R.layout.fragment_user, container,
				false);
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
	public void onRefresh() {
		initPath(1);
		// new UserListTask().execute(path, "refresh");
		new UserListTask(userList, swipeRefreshLayout, adapter).execute(path,
				"refresh");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(TAG, "onItemClick -- " + position);
		showUserItem(userList.get(position));
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Log.i(TAG, "onItemLongClick -- " + position);
		return false;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if (adapter.getCount() < maxVisibleIndex) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& lastVisibleIndex == adapter.getCount() - 1) {

				pageIndex++;
				initPath(pageIndex);
				swipeRefreshLayout.setRefreshing(true);
				new UserListTask(userList, swipeRefreshLayout, adapter)
						.execute(path, "loadMore");
			}
		} else {
			// Toast.makeText(getActivity(), "×îºóÒ»Ò³!",
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
				.findViewById(R.id.swipeRefreshLayoutUser);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		listView = (ListView) this.getActivity()
				.findViewById(R.id.listViewUser);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setOnScrollListener(this);
	}

	private void initData() {
		userList = new ArrayList<User>();
		adapter = new UserListAdapter(getActivity(), userList);
		listView.setAdapter(adapter);

		initPath(1);
		swipeRefreshLayout.setRefreshing(true);
		new UserListTask(userList, swipeRefreshLayout, adapter).execute(path,
				"init");
	}

	private void initPath(int pageIndex) {
		// http://wcf.open.cnblogs.com/blog/bloggers/recommend/{PAGEINDEX}/{PAGESIZE};
		path = AppMacros.RECOMMEND_BLOGS_PAGED;
		pageSize = AppMacros.PAGE_SIZE;
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);

		Log.i(TAG, "pageIndex£º" + pageIndex);
	}

	private void showUserItem(User user) {
		intent = new Intent(this.getActivity(), UserActivity.class);
		intent.putExtra("blogapp", user.getBlogapp());
		AppMacros.slelctedUserAvatar = user.getUserAvatar().toString();
		startActivity(intent);
	}
}
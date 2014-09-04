package com.arlen.cnblogs.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arlen.cnblogs.R;
import com.arlen.cnblogs.UserActivity;
import com.arlen.cnblogs.adapter.UserListAdapter;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;

public class UserFragment extends ListFragment {

	private List<User> userList;
	private String path;
	private int pageIndex;
	private int pageSize;

	private UserListAdapter adapter;

	private Handler handler = null;

	private Intent intent;

	public UserFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_user, container,
				false);
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showUserItem(userList.get(position));
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userList = new ArrayList<User>();

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					initData();
					handler.sendMessage(handler.obtainMessage(0, userList));
				} catch (Exception e) {
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
						ArrayList<User> users = (ArrayList<User>) msg.obj;
						BindListData(users);
					}
				}
			};

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initData() {
		userList.clear();

		path = Config.RECOMMEND_BLOGS_PAGED;
		pageIndex = 1;
		pageSize = Config.BLOG_PAGE_SIZE;
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);
		userList = AppUtils.getUserList(path);
	}

	private void BindListData(ArrayList<User> users) {
		adapter = new UserListAdapter(getActivity(), users);
		this.setListAdapter(adapter);
	}

	private void showUserItem(User userEntry) {
		intent = new Intent(this.getActivity(), UserActivity.class);
		intent.putExtra("blogapp", userEntry.getBlogapp());
		intent.putExtra("link", userEntry.getUserLink().toString());
		intent.putExtra("avatar", userEntry.getUserAvatar().toString());
		intent.putExtra("postcount", userEntry.getPostCount());
		intent.putExtra("updated",
				AppUtils.parseDateToString(userEntry.getUpdatedDate()));
		intent.putExtra("title", userEntry.getTitle());
		startActivity(intent);
	}

}
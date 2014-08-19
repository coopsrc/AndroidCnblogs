package com.arlen.cnblogs.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arlen.cnblogs.R;
import com.arlen.cnblogs.adapter.BlogListAdapter;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;

public class BlogHomeFragment extends ListFragment {

	private List<Blog> blogList;
	private String path;
	private int pageIndex;
	private int pageSize;

	private BlogListAdapter adapter;
	private Handler handler = null;

	public BlogHomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blog_home,
				container, false);
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// showBlogItem(blogList.get(position));
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
		Log.i("HomeFragment", "onCreate");

		blogList = new ArrayList<Blog>();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					initData();
					handler.sendMessage(handler.obtainMessage(0, blogList));
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
						ArrayList<Blog> blogs = (ArrayList<Blog>) msg.obj;
						BindListData(blogs);
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initData() {
		blogList.clear();

		path = Config.RECENT_BLOGS_PAGED;
		pageIndex = 1;
		pageSize = Config.BLOG_PAGE_SIZE;
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);
		Log.i("HomeFragment", "首页博客列表地址：" + path);
		Log.i("HomeFragment", "获取首页博客列表  --->  开始");
		blogList = AppUtils.getBlogList(path);
		Log.i("HomeFragment", "获取首页博客列表  --->  完成");
	}

	private void BindListData(ArrayList<Blog> blogs) {
		adapter = new BlogListAdapter(getActivity(), blogs);
		this.setListAdapter(adapter);
	}
}

package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.arlen.cnblogs.adapter.BlogListAdapter;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.fragment.BlogHomeFragment;
import com.arlen.cnblogs.task.BlogListTask;
import com.arlen.cnblogs.utils.AppMacros;
import com.arlen.cnblogs.utils.AppUtils;

public class UserActivity extends Activity implements OnItemClickListener,
		OnRefreshListener, OnScrollListener {

	private static final String TAG = BlogHomeFragment.class.getSimpleName();

	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView listView;

	private BlogListAdapter adapter;

	private String path;
	private int pageSize;
	private int pageIndex = 1;
	private List<Blog> blogList;

	private int lastVisibleIndex;
	private int maxVisibleIndex = 400;

	private Intent intent;

	private String blogApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setOverflowShowAlways();
		initComponent();

		initData();
	}

	private void initComponent() {
		swipeRefreshLayout = (SwipeRefreshLayout) this
				.findViewById(R.id.swipeRefreshLayoutUserBlog);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		listView = (ListView) this.findViewById(R.id.listViewUserBlog);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
	}

	private void initData() {
		intent = getIntent();
		blogApp = intent.getStringExtra("blogapp");
		this.getActionBar().setTitle(blogApp);

		blogList = new ArrayList<Blog>();
		adapter = new BlogListAdapter(this, blogList);
		listView.setAdapter(adapter);

		initPath(1);
		swipeRefreshLayout.setRefreshing(true);
		new BlogListTask(blogList, swipeRefreshLayout, adapter).execute(path,
				"init");
		Log.e(TAG, " init " + blogList.size());
	}

	public void initPath(int pageIndex) {
		path = AppMacros.USER_BLOGS_LIST_PAGED;
		pageSize = AppMacros.BLOG_PAGE_SIZE;
		path = path.replace("{BLOGAPP}", blogApp);
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case android.R.id.home:
			finish();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void showBlogItem(Blog blogEntry) {
		intent = new Intent(this, BlogActivity.class);

		if (blogEntry.getAuthorAvatar() == null) {
			intent.putExtra("avatar", "");
		} else {
			intent.putExtra("avatar", blogEntry.getAuthorAvatar().toString());
		}

		intent.putExtra("title", blogEntry.getBlogTitle());
		intent.putExtra("author", blogEntry.getAuthorName());
		intent.putExtra("published",
				AppUtils.parseDateToString(blogEntry.getPublishedDateDate()));
		intent.putExtra("id", blogEntry.getBlogId());
		intent.putExtra("link", blogEntry.getBlogTitle());

		startActivity(intent);
	}

	/**
	 * OverFlow显示图标
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method method = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					method.setAccessible(true);
					method.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	/**
	 * 统一OverFlow样式
	 */
	private void setOverflowShowAlways() {
		try {
			ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
			Field field = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			field.setAccessible(true);
			field.setBoolean(viewConfiguration, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		showBlogItem(blogList.get(position));
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (adapter.getCount() < maxVisibleIndex) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& lastVisibleIndex == adapter.getCount() - 1) {

				pageIndex++;
				initPath(pageIndex);
				swipeRefreshLayout.setRefreshing(true);
				new BlogListTask(blogList, swipeRefreshLayout, adapter)
						.execute(path, "loadMore");
				Log.e(TAG, " loadMore " + blogList.size());
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

	@Override
	public void onRefresh() {
		initPath(1);
		new BlogListTask(blogList, swipeRefreshLayout, adapter).execute(path,
				"refresh");
		Log.e(TAG, " refresh " + blogList.size());
	}
}

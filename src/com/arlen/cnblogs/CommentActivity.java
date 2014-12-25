package com.arlen.cnblogs;

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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.arlen.cnblogs.adapter.CommentListAdapter;
import com.arlen.cnblogs.dialog.ItemDialog;
import com.arlen.cnblogs.entity.Comment;
import com.arlen.cnblogs.task.CommentListTask;
import com.arlen.cnblogs.utils.AppMacros;

public class CommentActivity extends Activity implements
		OnItemLongClickListener, OnItemClickListener, OnRefreshListener,
		OnScrollListener {

	private static final String TAG = CommentActivity.class.getSimpleName();

	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView listView;

	private CommentListAdapter adapter;

	private List<Comment> commentList;
	private String path;
	private int pageIndex = 1;
	private int pageSize;

	private int lastVisibleIndex;
	private int maxVisibleIndex = 400;

	private String type;
	private int id;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		initComponent();
		receiveData();
		initData();
	}

	private void receiveData() {
		intent = getIntent();
		id = intent.getIntExtra("id", 0);
		type = intent.getStringExtra("type");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comment, menu);
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

	private void initComponent() {
		swipeRefreshLayout = (SwipeRefreshLayout) this
				.findViewById(R.id.swipeRefreshLayoutComment);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		listView = (ListView) this.findViewById(R.id.listViewComment);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setOnScrollListener(this);
	}

	private void initData() {
		commentList = new ArrayList<Comment>();
		adapter = new CommentListAdapter(this, commentList);
		listView.setAdapter(adapter);

		initPath(1);
		new CommentListTask(commentList, swipeRefreshLayout, adapter).execute(
				path, "init");
		swipeRefreshLayout.setRefreshing(true);
	}

	private void initPath(int pageIndex) {
		if (type.equals("blog")) {
			path = AppMacros.BLOGS_COMMENTS;
			path = path.replace("{POSTID}", "" + id);
		} else if (type.equals("news")) {
			path = AppMacros.NEWS_COMMENTS;
			path = path.replace("{CONTENTID}", "" + id);
		}

		pageSize = AppMacros.COMMENT_PAGE_SIZE;
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);

		Log.i(TAG, "pageIndex：" + pageIndex);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Log.e("onItemLongClick", "position   ---   " + position);
		String[] items = this.getResources().getStringArray(
				R.array.comment_list_dialog);
		ItemDialog dialog = new ItemDialog(this, items);
		dialog.setTitle("评论");
		dialog.show();

		return true;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (adapter.getCount() < maxVisibleIndex) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& lastVisibleIndex == adapter.getCount() - 1) {

				pageIndex++;
				initPath(pageIndex);
				swipeRefreshLayout.setRefreshing(true);
				new CommentListTask(commentList, swipeRefreshLayout, adapter)
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

	@Override
	public void onRefresh() {
		initPath(1);
		new CommentListTask(commentList, swipeRefreshLayout, adapter).execute(
				path, "refresh");

	}
}

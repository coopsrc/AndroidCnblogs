package com.arlen.cnblogs;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.arlen.cnblogs.adapter.CommentListAdapter;
import com.arlen.cnblogs.dialog.ItemDialog;
import com.arlen.cnblogs.entity.Comment;
import com.arlen.cnblogs.utils.AppMacros;
import com.arlen.cnblogs.utils.HttpUtil;

public class CommentActivity extends ListActivity implements
		OnItemLongClickListener {

	private List<Comment> commentList;
	private String path;
	private int pageIndex;
	private int pageSize;

	private String type;
	private int id;
	private Intent intent;

	private CommentListAdapter adapter;
	private static Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		receiveData();

		commentList = new ArrayList<Comment>();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					initData();
					handler.sendMessage(handler.obtainMessage(0, commentList));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		try {
			new Thread(runnable).start();
			handler = new Handler() {

				@SuppressWarnings("unchecked")
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (msg.what == 0) {
						ArrayList<Comment> comments = (ArrayList<Comment>) msg.obj;
						BindListData(comments);
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	private void initData() {
		commentList.clear();

		if (type.equals("blog")) {
			path = AppMacros.BLOGS_COMMENTS;
			path = path.replace("{POSTID}", "" + id);
		} else if (type.equals("news")) {
			path = AppMacros.NEWS_COMMENTS;
			path = path.replace("{CONTENTID}", "" + id);
		} else {
			;
		}
		pageIndex = 1;
		pageSize = AppMacros.COMMENT_PAGE_SIZE;
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);
		Log.i("HomeFragment", "首页博客列表地址：" + path);
		Log.i("HomeFragment", "获取首页博客列表  --->  开始");
		commentList = HttpUtil.getCommentList(path);
		Log.i("HomeFragment", "获取首页博客列表  --->  完成");
	}

	private void BindListData(List<Comment> comments) {
		adapter = new CommentListAdapter(this, comments);
		this.setListAdapter(adapter);
		this.getListView().setOnItemLongClickListener(this);
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
}

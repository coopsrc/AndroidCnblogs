package com.arlen.cnblogs;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.arlen.cnblogs.adapter.UserListAdapter;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.utils.AppMacros;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.HttpUtils;

public class SearchActivity extends ListActivity {

	private Button buttonSearch;
	private EditText textAuthor;

	private String authorName;
	private String path;

	private List<User> userList;

	private UserListAdapter adapter;

	private Handler handler = null;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		initComponent();
		setListener();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showUserItem(userList.get(position));
	}

	private void setListener() {

		buttonSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userList = new ArrayList<User>();

				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(2 * 1000);
							initData();
							handler.sendMessage(handler.obtainMessage(0,
									userList));
						} catch (Exception e) {
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
								ArrayList<User> users = (ArrayList<User>) msg.obj;
								BindListData(users);
							}
						}
					};

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void BindListData(ArrayList<User> users) {
		adapter = new UserListAdapter(this, users);
		this.setListAdapter(adapter);
	}

	private void showUserItem(User userEntry) {
		intent = new Intent(this, UserActivity.class);
		intent.putExtra("blogapp", userEntry.getBlogapp());
		startActivity(intent);
	}

	private void initData() {
		authorName = textAuthor.getText().toString();
		path = AppMacros.SEARCH_AUTHOR_BY_NAME;
		Log.i("SearchActivity", "textAuthor:" + authorName);
		if (textAuthor.getText() != null) {
			try {
				authorName = URLEncoder.encode(authorName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			path = path.replace("{TERM}", authorName);
		}
		Log.i("SearchActivity", "path:" + path);
		userList = HttpUtils.getUserList(path);
	}

	private void initComponent() {
		buttonSearch = (Button) findViewById(R.id.buttonSearch);
		textAuthor = (EditText) findViewById(R.id.editTextAuthor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
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
}

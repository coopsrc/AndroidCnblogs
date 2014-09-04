package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.arlen.cnblogs.adapter.UserBlogListAdapter;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity {

	private ImageView imageViewAvatar;
	private TextView textViewUserName;
	private TextView textViewUpdate;
	private TextView textViewUri;
	private ListView listViewBlogList;

	private Intent intent;
	private Handler handler = null;
	private String path;

	private String userAvatar;
	private String userName;
	private String updateDate;
	private String userUri;
	private int postCount;

	private String blogApp;
	private int pageIndex;
	private int pageSize;
	private List<Blog> blogList;
	private UserBlogListAdapter adapter;

	private ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setOverflowShowAlways();
		initComponent();

		initData();
		addData();
		addContent();
	}

	private void initComponent() {
		imageViewAvatar = (ImageView) findViewById(R.id.imageViewUserAvatar);
		textViewUserName = (TextView) findViewById(R.id.textViewUserName);
		textViewUpdate = (TextView) findViewById(R.id.textViewUserUpdate);
		textViewUri = (TextView) findViewById(R.id.textViewUserUri);
		listViewBlogList = (ListView) findViewById(R.id.listViewUserBlogList);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				128, 128);
		layoutParams.leftMargin = 15;
		layoutParams.topMargin = 15;
		layoutParams.rightMargin = 15;
		layoutParams.bottomMargin = 15;
		imageViewAvatar.setLayoutParams(layoutParams);

	}

	private void initData() {
		intent = getIntent();
		blogApp = intent.getStringExtra("blogapp");
		userUri = intent.getStringExtra("link");
		userAvatar = intent.getStringExtra("avatar");
		postCount = intent.getIntExtra("postcount", 0);
		updateDate = intent.getStringExtra("updated");
		userName = intent.getStringExtra("title");

		imageLoader.init(ImageLoaderConfiguration.createDefault(this
				.getApplicationContext()));

		path = Config.USER_BLOGS_LIST_PAGED;
		pageIndex = 1;
		pageSize = Config.BLOG_PAGE_SIZE;
		path = path.replace("{BLOGAPP}", blogApp);
		path = path.replace("{PAGEINDEX}", "" + pageIndex);
		path = path.replace("{PAGESIZE}", "" + pageSize);
	}

	private void addData() {
		imageLoader.displayImage(userAvatar, imageViewAvatar);
		textViewUserName.setText(userName + "\t(" + postCount + ")");
		textViewUpdate.setText(updateDate);
		textViewUri.setText(userUri);
	}

	private void addContent() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					blogList = new ArrayList<Blog>();
					blogList.clear();
					blogList = AppUtils.getBlogList(path);
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

	private void BindListData(ArrayList<Blog> blogs) {
		adapter = new UserBlogListAdapter(this, blogs);
		listViewBlogList.setAdapter(adapter);
	}
}

package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringEscapeUtils;

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
import android.widget.TextView;

import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BlogActivity extends Activity {

	private ImageView imageViewBlogAvatar;
	private TextView textViewBlogTitle;
	private TextView textViewBlogComments;
	private TextView textViewBlogContent;

	private Intent intent;
	private String authorAvatar;
	private String blogTitle;
	private String authorName;
	private String publishedDate;
	private int blogId;
	private String blogContent;

	private String path;
	private Handler handler = null;

	private ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setOverflowShowAlways();
		initComponent();
		initData();
		addData();
		addContent();
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

	private void initComponent() {
		imageViewBlogAvatar = (ImageView) findViewById(R.id.imageViewBlogAvatar);
		textViewBlogTitle = (TextView) findViewById(R.id.textViewBlogTitle);
		textViewBlogComments = (TextView) findViewById(R.id.textViewBlogComments);
		textViewBlogContent = (TextView) findViewById(R.id.textViewBlogContent);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				128, 128);
		layoutParams.leftMargin = 15;
		layoutParams.topMargin = 15;
		layoutParams.rightMargin = 15;
		layoutParams.bottomMargin = 15;
		imageViewBlogAvatar.setLayoutParams(layoutParams);
	}

	private void initData() {
		intent = getIntent();

		authorAvatar = intent.getStringExtra("avatar");
		blogTitle = intent.getStringExtra("title");
		authorName = intent.getStringExtra("author");
		publishedDate = intent.getStringExtra("published");
		blogId = intent.getIntExtra("id", 0);

		imageLoader.init(ImageLoaderConfiguration.createDefault(this
				.getApplicationContext()));

		path = Config.BLOGS_CONTENTS;
		path = path.replace("{POSTID}", "" + blogId);
	}

	private void addData() {
		imageLoader.displayImage(authorAvatar, imageViewBlogAvatar);
		textViewBlogTitle.setText(blogTitle);
		textViewBlogComments.setText("作者：" + authorName + "\r\n发布时间："
				+ publishedDate);
	}

	private void addContent() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					blogContent = AppUtils.getBlogContent(path);
					handler.sendMessage(handler.obtainMessage(0, blogContent));
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
						String content = (String) msg.obj;
						content = StringEscapeUtils.unescapeHtml(content);
						content = AppUtils.replaceXmlTag(content);
						textViewBlogContent.setText(content);
					}
				}

			};
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blog, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_comment:
			viewComment(blogId);
			break;
		case R.id.action_share:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void viewComment(int id) {
		Intent intent = new Intent(BlogActivity.this, CommentActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("type", "blog");
		startActivity(intent);
	}
}

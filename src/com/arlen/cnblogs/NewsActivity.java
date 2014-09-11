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

public class NewsActivity extends Activity {

	private ImageView imageViewTopinIcon;
	private TextView textViewNewsTitle;
	private TextView textViewPublisheDate;
	private TextView textViewNewsContent;

	private Intent intent;

	private String topicIcon;
	private String newsTitle;
	private String sourceName;
	private String publishDate;
	private String newsContent;
	private int newsId;

	private String path;
	private Handler handler = null;

	private ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setOverflowShowAlways();
		initComponent();
		initData();
		addData();
		addContent();
	}

	private void addContent() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					newsContent = AppUtils.getNewsContent(path);
					handler.sendMessage(handler.obtainMessage(0, newsContent));
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
						textViewNewsContent.setText(content);
					}
				}

			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addData() {
		imageLoader.displayImage(topicIcon, imageViewTopinIcon);
		textViewNewsTitle.setText(newsTitle);
		textViewPublisheDate.setText("新闻来源：" + sourceName + "\r\n发布时间："
				+ publishDate);
	}

	private void initData() {

		intent = getIntent();

		topicIcon = intent.getStringExtra("topicIcon");
		newsTitle = intent.getStringExtra("title");
		sourceName = intent.getStringExtra("sourceName");
		publishDate = intent.getStringExtra("published");
		newsId = intent.getIntExtra("id", 0);

		imageLoader.init(ImageLoaderConfiguration.createDefault(this
				.getApplicationContext()));

		path = Config.NEWS_CONTENT;
		path = path.replace("{CONTENTID}", "" + newsId);
	}

	private void initComponent() {
		imageViewTopinIcon = (ImageView) findViewById(R.id.imageViewNewsTopinIcon);
		textViewNewsTitle = (TextView) findViewById(R.id.textViewNewsTitle);
		textViewPublisheDate = (TextView) findViewById(R.id.textViewPublisheDate);
		textViewNewsContent = (TextView) findViewById(R.id.textViewNewsContent);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				128, 128);
		layoutParams.leftMargin = 15;
		layoutParams.topMargin = 15;
		layoutParams.rightMargin = 15;
		layoutParams.bottomMargin = 15;
		imageViewTopinIcon.setLayoutParams(layoutParams);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.news, menu);
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
			viewComment(newsId);
			break;
		case R.id.action_share:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void viewComment(int id) {
		Intent intent = new Intent(NewsActivity.this, CommentActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("type", "news");
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
}

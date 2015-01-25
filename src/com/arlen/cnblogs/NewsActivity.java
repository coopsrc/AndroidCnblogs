package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlen.cnblogs.task.ImageLoadTask;
import com.arlen.cnblogs.task.NewsContentTask;
import com.arlen.cnblogs.utils.AppConfig;
import com.arlen.cnblogs.utils.AppUtils;

public class NewsActivity extends Activity {

	private ImageView imageViewTopinIcon;
	private TextView textViewNewsTitle;
	private TextView textViewPublisheDate;
	private WebView webViewNewsContent;

	private Intent intent;

	private String topicIcon;
	private String newsTitle;
	private String sourceName;
	private String publishDate;
	private int newsId;

	private String path;

	// share
	private String newsLink;

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
	}

	private void addData() {
		new ImageLoadTask(imageViewTopinIcon).execute(topicIcon, "topicIcon");
		textViewNewsTitle.setText(newsTitle);
		textViewPublisheDate.setText("新闻来源：" + sourceName + "\r\n发布时间："
				+ publishDate);

		new NewsContentTask(webViewNewsContent).execute(path);
	}

	private void initData() {

		intent = getIntent();

		topicIcon = intent.getStringExtra("topicIcon");
		newsTitle = intent.getStringExtra("title");
		sourceName = intent.getStringExtra("sourceName");
		publishDate = intent.getStringExtra("published");
		newsId = intent.getIntExtra("id", 0);

		newsLink = intent.getStringExtra("link");

		path = AppConfig.NEWS_CONTENT;
		path = path.replace("{CONTENTID}", "" + newsId);
	}

	private void initComponent() {
		imageViewTopinIcon = (ImageView) findViewById(R.id.imageViewNewsTopinIcon);
		textViewNewsTitle = (TextView) findViewById(R.id.textViewNewsTitle);
		textViewPublisheDate = (TextView) findViewById(R.id.textViewPublisheDate);
		webViewNewsContent = (WebView) findViewById(R.id.webViewNewsContent);
		webViewNewsContent.setHorizontalScrollBarEnabled(false);// 设置水平滚动条，true表示允许使用
		WebSettings webSettings = webViewNewsContent.getSettings();
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setCacheMode(1);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webViewNewsContent.loadDataWithBaseURL(null, "<center/>正在加载 ...<hr>",
				"text/html", "UTF-8", null);

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
			AppUtils.ShareText(this, newsLink);
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

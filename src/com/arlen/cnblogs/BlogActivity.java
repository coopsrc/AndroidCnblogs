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

import com.arlen.cnblogs.task.BlogContentTask;
import com.arlen.cnblogs.task.ImageLoadTask;
import com.arlen.cnblogs.utils.AppMacros;
import com.arlen.cnblogs.utils.AppUtils;

public class BlogActivity extends Activity {

  private ImageView imageViewBlogAvatar;
  private TextView textViewBlogTitle;
  private TextView textViewBlogComments;
  private WebView webViewBlogContent;

  private Intent intent;
  private String authorAvatar;
  private String blogTitle;
  private String authorName;
  private String publishedDate;
  private int blogId;

  private String path;

  // share
  private String blogLink;

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
  }

  /**
   * 统一OverFlow样式
   */
  private void setOverflowShowAlways() {
    try {
      ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
      Field field = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
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
    webViewBlogContent = (WebView) findViewById(R.id.webViewBlogContent);
    webViewBlogContent.setHorizontalScrollBarEnabled(false);// 设置水平滚动条，true表示允许使用
    WebSettings webSettings = webViewBlogContent.getSettings();
    webSettings.setDefaultTextEncodingName("UTF-8");
    webSettings.setCacheMode(1);
    webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
    webViewBlogContent.loadDataWithBaseURL(null, "<center/>正在加载 ...<hr>", "text/html", "UTF-8",
        null);

    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(128, 128);
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

    blogLink = intent.getStringExtra("link");

    path = AppMacros.BLOGS_CONTENTS;
    path = path.replace("{POSTID}", "" + blogId);
  }

  private void addData() {
    new ImageLoadTask(imageViewBlogAvatar).execute(authorAvatar, "avatar");
    textViewBlogTitle.setText(blogTitle);
    textViewBlogComments.setText("作者：" + authorName + "\r\n发布时间：" + publishedDate);
    new BlogContentTask(webViewBlogContent).execute(path);
  }

  /**
   * OverFlow显示图标
   */
  @Override
  public boolean onMenuOpened(int featureId, Menu menu) {
    if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
      if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
        try {
          Method method =
              menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
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
        AppUtils.ShareText(this, blogLink);
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

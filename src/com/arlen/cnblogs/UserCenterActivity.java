package com.arlen.cnblogs;

import java.lang.reflect.Method;

import com.arlen.cnblogs.bean.PersonInfo;
import com.arlen.cnblogs.task.PersonInfoTask;
import com.arlen.cnblogs.utils.HttpThread;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class UserCenterActivity extends Activity {

  private ImageView imageViewAvatar;
  private TextView textViewNickName;
  private TextView textViewCnblogsAge;
  private TextView textViewFollowers;
  private TextView textViewFollowees;
  private SwipeRefreshLayout refreshLayout;

  private PersonInfo personInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_center);

    ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    initComponent();

    initData();

    setAvatar();
  }

  private void initComponent() {
    imageViewAvatar = (ImageView) this.findViewById(R.id.imageViewPersonalAvatar);
    textViewNickName = (TextView) this.findViewById(R.id.textViewPersonalNickName);
    textViewCnblogsAge = (TextView) this.findViewById(R.id.textViewPersonalCnblogsAge);
    textViewFollowers = (TextView) this.findViewById(R.id.textViewPersonalFollowers);
    textViewFollowees = (TextView) this.findViewById(R.id.textViewPersonalFollowees);
    refreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeRefreshLayoutPersonalBlog);
  }

  private void initData() {
    PersonInfoTask task = new PersonInfoTask(personInfo);
    task.setTextViewNickName(textViewNickName);
    task.setTextViewCnblogsAge(textViewCnblogsAge);
    task.setTextViewFollowers(textViewFollowers);
    task.setTextViewFollowees(textViewFollowees);
    task.execute();
  }

  private void setAvatar() {
    ProgressDialog proDialog = ProgressDialog.show(this, "",
      "正在获取数据……", true, true);
    HttpThread jsonThread = new HttpThread(this, proDialog);
    String url = "http://home.cnblogs.com/user/CurrentIngUserInfo";
    String[] key = { "type" };
    String[] value = { "0" };
    String[] jsonKey = { "Avatar", "Username"};
    jsonThread.setUrl(url);
    jsonThread.setKey(key);
    jsonThread.setValue(value);
    jsonThread.setJsonKey(jsonKey);
    jsonThread.start();
  }

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
    getMenuInflater().inflate(R.menu.user_center, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    } else if (id == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

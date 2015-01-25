package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.arlen.cnblogs.task.DraftTask;
import com.arlen.cnblogs.task.PublishTask;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class PublishActivity extends Activity implements OnClickListener {

  private EditText editTextTitle;
  private EditText editTextContent;
  private Button buttonDraft;
  private Button buttonPublish;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_publish);

    ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    setOverflowShowAlways();

    initComponent();
  }

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
    getMenuInflater().inflate(R.menu.publish, menu);
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

  private void initComponent() {
    editTextTitle = (EditText) findViewById(R.id.editTextBlogTitle);
    editTextContent = (EditText) findViewById(R.id.editTextBlogContent);
    buttonDraft = (Button) findViewById(R.id.buttonBlogDraft);
    buttonPublish = (Button) findViewById(R.id.buttonBlogPublish);

    buttonPublish.setOnClickListener(this);
    buttonDraft.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    String title = editTextTitle.getText().toString();
    String content = editTextContent.getText().toString();

    if (v.getId() == buttonPublish.getId()) {
      PublishTask task = new PublishTask(this);
      task.setTitle(title);
      task.setContent(content);
      task.execute();
    } else if (v.getId() == buttonDraft.getId()) {
      DraftTask task = new DraftTask(this);
      task.setTitle(title);
      task.setContent(content);
      task.execute();
    }
  }
}

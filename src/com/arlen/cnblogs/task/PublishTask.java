package com.arlen.cnblogs.task;

import com.arlen.cnblogs.PublishActivity;
import com.arlen.cnblogs.utils.LoginUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class PublishTask extends AsyncTask<Void, Void, Void> {
  private Context context;

  private String title;
  private String content;

  private boolean publishResult = false;

  public PublishTask(Context context) {
    super();
    this.context = context;
  }

  @Override
  protected Void doInBackground(Void... params) {
    publishResult = LoginUtils.Releasee(title, content);
    return null;
  }

  @Override
  protected void onPostExecute(Void result) {
    super.onPostExecute(result);

    if (publishResult) {
      Toast.makeText(context, "发布成功", Toast.LENGTH_LONG).show();
      ((PublishActivity) context).finish();
    } else {
      Toast.makeText(context, "发布失败", Toast.LENGTH_LONG).show();
    }

  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}

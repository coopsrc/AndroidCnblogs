package com.arlen.cnblogs.task;

import java.util.List;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.arlen.cnblogs.adapter.UserListAdapter;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.utils.HttpUtils;

public class SearchTask extends AsyncTask<String, Void, Void> {
  private UserListAdapter adapter;
  private List<User> userList;
  private ProgressBar progressBar;

  @Override
  protected Void doInBackground(String... params) {
    userList = HttpUtils.getUserList(params[0]);
    return null;
  }
  
  @Override
  protected void onPostExecute(Void result) {
    super.onPostExecute(result);
    progressBar.setVisibility(View.GONE);
    adapter.notifyDataSetChanged();
  }

  public UserListAdapter getAdapter() {
    return adapter;
  }

  public void setAdapter(UserListAdapter adapter) {
    this.adapter = adapter;
  }

  public List<User> getUserList() {
    return userList;
  }

  public void setUserList(List<User> userList) {
    this.userList = userList;
  }

  public ProgressBar getProgressBar() {
    return progressBar;
  }

  public void setProgressBar(ProgressBar progressBar) {
    this.progressBar = progressBar;
  }

}

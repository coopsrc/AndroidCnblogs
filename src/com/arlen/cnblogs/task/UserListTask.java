package com.arlen.cnblogs.task;

import java.util.ArrayList;
import java.util.List;

import com.arlen.cnblogs.adapter.UserListAdapter;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.HttpUtils;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

public class UserListTask extends AsyncTask<String, Void, Void> {
  private List<User> userList;
  private List<User> newList = new ArrayList<User>();
  private SwipeRefreshLayout swipeRefreshLayout;
  private UserListAdapter adapter;

  public UserListTask(List<User> userList, SwipeRefreshLayout swipeRefreshLayout,
      UserListAdapter adapter) {
    super();
    this.userList = userList;
    this.swipeRefreshLayout = swipeRefreshLayout;
    this.adapter = adapter;
  }

  protected Void doInBackground(String... params) {

    newList = HttpUtils.getUserList(params[0]);

    if (params[1].equals("init")) {
      userList.addAll(newList);
    } else if (params[1].equals("refresh")) {
      newList.addAll(userList);
      AppUtils.removeDuplicate(newList);
      userList.clear();
      userList.addAll(newList);
    } else if (params[1].equals("loadMore")) {
      userList.addAll(newList);
      AppUtils.removeDuplicate(userList);
    }
    return null;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    newList.clear();
  }

  @Override
  protected void onPostExecute(Void result) {
    super.onPostExecute(result);

    swipeRefreshLayout.setRefreshing(false);
    if (userList != null) {
      adapter.updataUserList(userList);
    }
    adapter.notifyDataSetChanged();
  }

}

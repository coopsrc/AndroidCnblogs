package com.arlen.cnblogs.task;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.arlen.cnblogs.adapter.UserBlogListAdapter;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.HttpUtils;

public class UserBlogListTask extends AsyncTask<String, Void, Void> {
  private List<Blog> blogList;
  private List<Blog> newList = new ArrayList<Blog>();
  private SwipeRefreshLayout swipeRefreshLayout;
  private UserBlogListAdapter adapter;

  public UserBlogListTask(List<Blog> blogList, SwipeRefreshLayout swipeRefreshLayout,
      UserBlogListAdapter adapter) {
    super();
    this.blogList = blogList;
    this.swipeRefreshLayout = swipeRefreshLayout;
    this.adapter = adapter;
  }

  protected Void doInBackground(String... params) {

    newList = HttpUtils.getBlogList(params[0]);

    if (newList != null) {
      if (params[1].equals("init")) {
        blogList.addAll(newList);
      } else if (params[1].equals("refresh")) {
        newList.addAll(blogList);
        AppUtils.removeDuplicate(newList);
        blogList.clear();
        blogList.addAll(newList);
      } else if (params[1].equals("loadMore")) {
        blogList.addAll(newList);
        AppUtils.removeDuplicate(blogList);
      }
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
    adapter.updataBlogList(blogList);
    adapter.notifyDataSetChanged();
  }

}

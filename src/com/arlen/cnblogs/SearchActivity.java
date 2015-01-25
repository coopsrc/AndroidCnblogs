package com.arlen.cnblogs;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.arlen.cnblogs.adapter.UserListAdapter;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.task.SearchTask;
import com.arlen.cnblogs.utils.AppConfig;

public class SearchActivity extends ListActivity implements OnClickListener {

  private Button buttonSearch;
  private EditText textAuthor;
  private ProgressBar progressBar;

  private String authorName;
  private String path;

  private List<User> userList = new ArrayList<User>();

  private UserListAdapter adapter;

  private Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    initComponent();
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    showUserItem(userList.get(position));
  }

  private void showUserItem(User userEntry) {
    intent = new Intent(this, UserActivity.class);
    if (userEntry.getUserAvatar() != null) {
      intent.putExtra("avatar", userEntry.getUserAvatar().toString());
    }
    intent.putExtra("blogapp", userEntry.getBlogapp());
    startActivity(intent);
  }

  private void initComponent() {
    buttonSearch = (Button) findViewById(R.id.buttonSearch);
    textAuthor = (EditText) findViewById(R.id.editTextAuthor);
    progressBar = (ProgressBar) findViewById(R.id.progressBarSearch);

    buttonSearch.setOnClickListener(this);

    adapter = new UserListAdapter(this, userList);
    this.setListAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v) {
    InputMethodManager imm =
        (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm.isActive()) {
      imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

    }

    progressBar.setVisibility(View.VISIBLE);

    authorName = textAuthor.getText().toString().trim();
    path = AppConfig.SEARCH_AUTHOR_BY_NAME;
    Log.i("SearchActivity", "textAuthor:" + authorName);
    if (textAuthor.getText() != null) {
      try {
        authorName = URLEncoder.encode(authorName, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      path = path.replace("{TERM}", authorName);
    }
    Log.i("SearchActivity", "path:" + path);
    SearchTask task = new SearchTask();
    task.setAdapter(adapter);
    task.setUserList(userList);
    task.setProgressBar(progressBar);
    task.execute(path);

  }
}

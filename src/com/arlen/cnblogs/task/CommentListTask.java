package com.arlen.cnblogs.task;

import java.util.ArrayList;
import java.util.List;

import com.arlen.cnblogs.adapter.CommentListAdapter;
import com.arlen.cnblogs.entity.Comment;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.HttpUtils;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

public class CommentListTask extends AsyncTask<String, Void, Void> {

	private static final String TAG = CommentListTask.class.getSimpleName();
	private List<Comment> commentList;
	private List<Comment> newList = new ArrayList<Comment>();
	private SwipeRefreshLayout swipeRefreshLayout;
	private CommentListAdapter adapter;

	public CommentListTask(List<Comment> commentList,
			SwipeRefreshLayout swipeRefreshLayout, CommentListAdapter adapter) {
		super();
		this.commentList = commentList;
		this.swipeRefreshLayout = swipeRefreshLayout;
		this.adapter = adapter;
	}

	@Override
	protected Void doInBackground(String... params) {
		newList = HttpUtils.getCommentList(params[0]);

		if (params[1].equals("init")) {
			commentList.addAll(newList);
		} else if (params[1].equals("refresh")) {
			newList.addAll(commentList);
			AppUtils.removeDuplicate(newList);
			commentList.clear();
			commentList.addAll(newList);
		} else if (params[1].equals("loadMore")) {
			commentList.addAll(newList);
			AppUtils.removeDuplicate(commentList);
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
		adapter.updataBlogList(commentList);
		adapter.notifyDataSetChanged();

		Log.e(TAG, "commentList Size : " + commentList.size());
	}
}

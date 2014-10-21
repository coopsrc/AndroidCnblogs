package com.arlen.cnblogs.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arlen.cnblogs.R;
import com.arlen.cnblogs.entity.Comment;
import com.arlen.cnblogs.utils.AppUtils;

public class CommentListAdapter extends BaseAdapter {

	private Context context;
	private List<Comment> commentList;

	public CommentListAdapter(Context context, List<Comment> commentList) {
		super();
		this.context = context;
		this.commentList = commentList;

		Log.i("HomeListAdapter", "HomeListAdapter 初始化完成");
	}

	@Override
	public int getCount() {
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		return commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("HomeListAdapter", "getView" + position + " 开始");
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.comment_iten, null);
			ItemViewCache itemViewCache = new ItemViewCache();

			itemViewCache.index = (TextView) convertView
					.findViewById(R.id.textViewIndex);
			itemViewCache.published = (TextView) convertView
					.findViewById(R.id.textViewDate);
			itemViewCache.authorName = (TextView) convertView
					.findViewById(R.id.textViewName);
			itemViewCache.content = (TextView) convertView
					.findViewById(R.id.textViewContent);

			convertView.setTag(itemViewCache);
		}

		ItemViewCache cache = (ItemViewCache) convertView.getTag();
		Comment comment = commentList.get(position);

		cache.index.setText("# " + (position + 1) + " 楼");
		cache.published.setText(AppUtils.parseDateToString(comment
				.getPublishedDate()));
		cache.authorName.setText(comment.getAuthorName());
		String content = comment.getCommentContent();
		cache.content.setText(content);

		Log.i("HomeListAdapter", "getView" + position + " 完成");
		Log.i("HomeListAdapter", "==========================");
		return convertView;
	}

	private static class ItemViewCache {
		private TextView index;
		private TextView published;
		private TextView authorName;
		private TextView content;
	}
}

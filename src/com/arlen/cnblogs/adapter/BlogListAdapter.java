package com.arlen.cnblogs.adapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlen.cnblogs.R;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.task.ImageLoadTask;
import com.arlen.cnblogs.utils.AppUtils;

public class BlogListAdapter extends BaseAdapter {

	private Context context;
	private List<Blog> blogList;

	public BlogListAdapter(Context context, List<Blog> blogList) {
		super();
		this.context = context;
		this.blogList = blogList;

		Log.i("HomeListAdapter", "HomeListAdapter 初始化完成");
	}

	@Override
	public int getCount() {
		return blogList.size();
	}

	@Override
	public Object getItem(int position) {
		return blogList.get(position);
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
					R.layout.blog_item, null);
			ItemViewCache itemViewCache = new ItemViewCache();

			itemViewCache.blogTitle = (TextView) convertView
					.findViewById(R.id.textViewBlogItemTitle);
			itemViewCache.authorAvatar = (ImageView) convertView
					.findViewById(R.id.imageViewBlogItemAvatar);
			itemViewCache.blogSummary = (TextView) convertView
					.findViewById(R.id.textViewBlogItemSummary);
			itemViewCache.authorName = (TextView) convertView
					.findViewById(R.id.textViewBlogItemAuthor);
			itemViewCache.publishedDate = (TextView) convertView
					.findViewById(R.id.textViewBlogItemPublisheddate);
			itemViewCache.blogComments = (TextView) convertView
					.findViewById(R.id.textViewBlogItemComment);
			itemViewCache.blogViews = (TextView) convertView
					.findViewById(R.id.textViewBlogItemView);
			itemViewCache.blogDiggs = (TextView) convertView
					.findViewById(R.id.textViewBlogItemDiggs);
			convertView.setTag(itemViewCache);
		}

		ItemViewCache cache = (ItemViewCache) convertView.getTag();
		Blog blog = blogList.get(position);

		cache.blogTitle.setText(blog.getBlogTitle());

		URL url = blog.getAuthorAvatar();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				128, 128);
		layoutParams.leftMargin = 5;
		layoutParams.rightMargin = 5;
		layoutParams.topMargin = 5;
		cache.authorAvatar.setLayoutParams(layoutParams);
		if (url != null) {
			new ImageLoadTask(cache.authorAvatar).execute(url.toString(),
					"avatar");
		}
		cache.blogSummary.setText(blog.getBlogSummary());

		cache.authorName.setText(blog.getAuthorName());

		Date date = blog.getPublishedDateDate();
		cache.publishedDate.setText(AppUtils.parseDateToChinese(date));

		cache.blogComments.setText("评论:" + blog.getBlogComments());

		cache.blogViews.setText("\t浏览:" + blog.getBlogViews());

		cache.blogDiggs.setText("\t推荐:" + blog.getBlogDiggs());

		Log.i("HomeListAdapter", "getView" + position + " 完成");
		Log.i("HomeListAdapter", "==========================");
		return convertView;
	}

	private static class ItemViewCache {
		private TextView blogTitle;
		private ImageView authorAvatar;
		private TextView blogSummary;
		private TextView authorName;
		private TextView publishedDate;
		private TextView blogComments;
		private TextView blogViews;
		private TextView blogDiggs;
	}

	public void updataBlogList(List<Blog> newList) {
		List<Blog> listTemp = new ArrayList<Blog>();
		listTemp.addAll(newList);
		blogList.clear();
		blogList.addAll(listTemp);
	}
}

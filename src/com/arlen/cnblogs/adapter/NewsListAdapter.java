package com.arlen.cnblogs.adapter;

import java.net.URL;
import java.util.ArrayList;
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
import com.arlen.cnblogs.entity.News;
import com.arlen.cnblogs.utils.AppUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class NewsListAdapter extends BaseAdapter {

	private Context context;
	private List<News> newsList;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	public NewsListAdapter(Context context, List<News> blogList) {
		super();
		this.context = context;
		this.newsList = blogList;

		imageLoader.init(ImageLoaderConfiguration.createDefault(context));

		Log.i("NewsListAdapter", "HomeListAdapter 初始化完成");
	}

	@Override
	public int getCount() {
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		return newsList.get(position);
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
					R.layout.news_item, null);
			ItemViewCache itemViewCache = new ItemViewCache();

			itemViewCache.newsTitle = (TextView) convertView
					.findViewById(R.id.textViewNewsItemTitle);
			itemViewCache.topicIcon = (ImageView) convertView
					.findViewById(R.id.imageViewNewsItemTopicIcon);
			itemViewCache.newsSummary = (TextView) convertView
					.findViewById(R.id.textViewNewsItemSummary);
			itemViewCache.newsTopic = (TextView) convertView
					.findViewById(R.id.textViewNewsItemTopic);
			itemViewCache.sourceName = (TextView) convertView
					.findViewById(R.id.textViewNewsItemSourceName);
			itemViewCache.publishedDate = (TextView) convertView
					.findViewById(R.id.textViewNewsItemPublisheddate);
			itemViewCache.newsDiggs = (TextView) convertView
					.findViewById(R.id.textViewNewsItemDiggs);
			itemViewCache.newsgViews = (TextView) convertView
					.findViewById(R.id.textViewNewsItemView);
			itemViewCache.newsComments = (TextView) convertView
					.findViewById(R.id.textViewNewsItemComment);
			convertView.setTag(itemViewCache);
		}

		ItemViewCache cache = (ItemViewCache) convertView.getTag();
		News news = newsList.get(position);

		cache.newsTitle.setText(news.getNewsTitle());

		URL url = news.getTopicIcon();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				128, 128);
		layoutParams.leftMargin = 5;
		layoutParams.rightMargin = 5;
		layoutParams.topMargin = 5;
		cache.topicIcon.setLayoutParams(layoutParams);
		if (url != null) {
			imageLoader.displayImage(url.toString(), cache.topicIcon);
		}

		cache.newsSummary.setText(news.getNewsSummary());

		if (!news.getNewsTopic().isEmpty()) {
			cache.newsTopic.setText("主题：" + news.getNewsTopic());
		} else {
			cache.newsTopic.setText("");
		}

		if (!news.getSourceName().isEmpty()) {
			if (news.getNewsTopic().isEmpty()) {
				cache.sourceName.setText("来源：" + news.getSourceName());
			} else {
				cache.sourceName.setText("\t来源：" + news.getSourceName());
			}
		} else {
			cache.sourceName.setText("");
		}

		cache.publishedDate.setText(AppUtils.parseDateToChinese(news
				.getPublishedDate()));
		cache.newsComments.setText("评论:" + news.getNewsComments());
		cache.newsgViews.setText("\t浏览:" + news.getNewsViews());
		cache.newsDiggs.setText("\t推荐:" + news.getNewsDiggs());

		Log.i("HomeListAdapter", "getView" + position + " 完成");
		Log.i("HomeListAdapter", "==========================");
		return convertView;
	}

	private static class ItemViewCache {
		private TextView newsTitle;
		private ImageView topicIcon;
		private TextView newsSummary;
		private TextView newsTopic;
		private TextView sourceName;
		private TextView publishedDate;
		private TextView newsComments;
		private TextView newsgViews;
		private TextView newsDiggs;
	}
	
	public void updataNewsList(List<News> newList) {
		List<News> listTemp = new ArrayList<News>();
		listTemp.addAll(newList);
		newsList.clear();
		newsList.addAll(listTemp);
	}
}

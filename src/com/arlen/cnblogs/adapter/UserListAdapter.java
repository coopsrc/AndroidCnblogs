package com.arlen.cnblogs.adapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.arlen.cnblogs.R;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.utils.AppUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter {
	private Context context;
	private List<User> userList;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	public UserListAdapter(Context context, List<User> userList) {
		super();
		this.context = context;
		this.userList = userList;
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	public int getCount() {
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.user_item, null);
			ItemViewCache itemViewCache = new ItemViewCache();
			itemViewCache.imageViewUser = (ImageView) convertView
					.findViewById(R.id.imageViewUser);
			itemViewCache.textViewUser = (TextView) convertView
					.findViewById(R.id.textViewUser);
			itemViewCache.textViewCount = (TextView) convertView
					.findViewById(R.id.textViewCount);
			itemViewCache.textViewDate = (TextView) convertView
					.findViewById(R.id.textViewDate);
			convertView.setTag(itemViewCache);
		}

		ItemViewCache cache = (ItemViewCache) convertView.getTag();
		User user = userList.get(position);
		URL url = user.getUserAvatar();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				128, 128);
		layoutParams.leftMargin = 5;
		layoutParams.rightMargin = 5;
		layoutParams.topMargin = 5;
		layoutParams.bottomMargin = 5;
		cache.imageViewUser.setLayoutParams(layoutParams);
		if (url != null) {
			imageLoader.displayImage(url.toString(), cache.imageViewUser);
		}

		cache.textViewUser.setText(user.getTitle());
		cache.textViewCount.setText("随笔总数：" + user.getPostCount());
		cache.textViewDate.setText("最后更新："
				+ AppUtils.parseDateToString(user.getUpdatedDate()));

		return convertView;
	}

	public static class ItemViewCache {
		private ImageView imageViewUser;
		private TextView textViewUser;
		private TextView textViewCount;
		private TextView textViewDate;
	}

	public void updataUserList(List<User> newList) {
		List<User> listTemp = new ArrayList<User>();
		listTemp.addAll(newList);
		userList.clear();
		userList.addAll(listTemp);
	}
}

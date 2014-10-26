package com.arlen.cnblogs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.arlen.cnblogs.R;

public class DrawerListAdapter extends BaseAdapter {
	private Context context;
	private String[] contenct;
	private int[] images;

	public DrawerListAdapter(Context context, String[] contenct, int[] images) {
		super();
		this.context = context;
		this.contenct = contenct;
		this.images = images;
	}

	@Override
	public int getCount() {
		return contenct.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
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
					R.layout.drawer_list_item, null);
			ItemViewCache itemViewCache = new ItemViewCache();
			itemViewCache.imageView = (ImageView) convertView
					.findViewById(R.id.imageViewIcon);
			itemViewCache.textView = (TextView) convertView
					.findViewById(R.id.textViewTitle);
			convertView.setTag(itemViewCache);
		}

		ItemViewCache chche = (ItemViewCache) convertView.getTag();
		chche.imageView.setImageResource(images[position]);
		chche.textView.setText(contenct[position]);
		return convertView;
	}

	private class ItemViewCache {
		public ImageView imageView;
		public TextView textView;
	}

}
package com.arlen.cnblogs.view;

import com.arlen.cnblogs.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemDialog extends Dialog {

	private Context context;
	private String[] items;
	private ListView listView;

	public ItemDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public ItemDialog(Context context) {
		super(context);
		this.context = context;
	}

	public ItemDialog(Context context, String[] items) {
		super(context);
		this.context = context;
		this.items = items;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.item_dialog);
		listView = (ListView) findViewById(R.id.listViewBlogDialog);
		listView.setAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, items));
	}
}

package com.arlen.cnblogs.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.arlen.cnblogs.BlogActivity;
import com.arlen.cnblogs.R;
import com.arlen.cnblogs.adapter.BlogListAdapter;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;
import com.arlen.cnblogs.view.ItemDialog;

public class BlogReaddingFragment extends ListFragment implements OnItemLongClickListener {

	private List<Blog> blogList;
	private String path;
	private int pageSize;

	private BlogListAdapter adapter;
	private Handler handler = null;

	private Intent intent;

	public BlogReaddingFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blog_readding,
				container, false);
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showBlogItem(blogList.get(position));
	}

	private void showBlogItem(Blog blogEntry) {
		intent = new Intent(this.getActivity(), BlogActivity.class);

		if (blogEntry.getAuthorAvatar() != null) {
			intent.putExtra("avatar", blogEntry.getAuthorAvatar().toString());
		} else {
			intent.putExtra("avatar",
					"http://pic.cnitblog.com/avatar/413207/20131211125235.png");
		}
		intent.putExtra("title", blogEntry.getBlogTitle());
		intent.putExtra("author", blogEntry.getAuthorName());
		intent.putExtra("published",
				AppUtils.parseDateToString(blogEntry.getPublishedDateDate()));
		intent.putExtra("id", blogEntry.getBlogId());
		intent.putExtra("link", blogEntry.getBlogTitle());

		startActivity(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ReaddingFragment", "onCreate");

		blogList = new ArrayList<Blog>();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
					initData();
					handler.sendMessage(handler.obtainMessage(0, blogList));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		try {
			new Thread(runnable).start();
			handler = new Handler() {

				@SuppressWarnings("unchecked")
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (msg.what == 0) {
						ArrayList<Blog> blogs = (ArrayList<Blog>) msg.obj;
						BindListData(blogs);
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initData() {
		blogList.clear();

		path = Config.TOP_VIEW_POSTS_48_HOUR;
		pageSize = Config.BLOG_PAGE_SIZE;
		path = path.replace("{ITEMCOUNT}", "" + pageSize);
		Log.i("ReaddingFragment", "首页博客列表地址：" + path);
		Log.i("ReaddingFragment", "获取首页博客列表  --->  开始");
		blogList = AppUtils.getBlogList(path);
		Log.i("ReaddingFragment", "获取首页博客列表  --->  完成");
	}

	private void BindListData(ArrayList<Blog> blogs) {
		adapter = new BlogListAdapter(getActivity(), blogs);
		this.setListAdapter(adapter);
		this.getListView().setOnItemLongClickListener(this);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Log.e("onItemLongClick", "position   ---   " + position);
		String[] items = getActivity().getResources().getStringArray(
				R.array.blog_list_dialog);
		ItemDialog dialog = new ItemDialog(getActivity(), items);
		dialog.setTitle("博客");
		
		dialog.TAG = "blog";
		dialog.blogEntry = blogList.get(position);
		
		dialog.show();

		return true;
	}
}

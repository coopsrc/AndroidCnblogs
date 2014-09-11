package com.arlen.cnblogs.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arlen.cnblogs.BlogActivity;
import com.arlen.cnblogs.CommentActivity;
import com.arlen.cnblogs.NewsActivity;
import com.arlen.cnblogs.R;
import com.arlen.cnblogs.UserActivity;
import com.arlen.cnblogs.entity.Blog;
import com.arlen.cnblogs.entity.Comment;
import com.arlen.cnblogs.entity.News;
import com.arlen.cnblogs.entity.User;
import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.Config;

public class ItemDialog extends Dialog implements OnItemClickListener {

	private Context context;
	private String[] items;
	private ListView listView;
	public int selectIndex = 0;

	public Blog blogEntry;
	public News newsEntry;
	public Comment commentEntry;
	public String commentString;
	public String TAG;

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

		// this.setCanceledOnTouchOutside(false);

		listView = (ListView) findViewById(R.id.listViewBlogDialog);
		listView.setAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, items));
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectIndex = position;
		Log.i("ItemDialog", "selectIndex   ---   " + selectIndex);

		String selectItem = items[position];

		if (selectItem.equals("查看内容")) {
			this.hide();
			if (TAG.equals("blog")) {
				viewBlogContent();
			} else if (TAG.equals("news")) {
				viewNewsContent();
			}
		} else if (selectItem.equals("查看评论")) {
			this.hide();
			if (TAG.equals("blog")) {
				viewComment(blogEntry.getBlogId(), "blog");
			} else if (TAG.equals("news")) {
				viewComment(newsEntry.getNewsId(), "news");
			}
		} else if (selectItem.equals("查看作者")) {
			this.hide();
			viewAuthor();
		} else if (selectItem.equals("回复")) {
			this.hide();
			addComment(commentString, "回复");
		} else if (selectItem.equals("引用")) {
			this.hide();
			addComment(commentString, "引用");
		} else if (selectItem.equals("取消")) {
			this.hide();
		}

		return;
	}

	private void viewBlogContent() {
		Intent intent = new Intent(context, BlogActivity.class);

		if (blogEntry.getAuthorAvatar() != null) {
			intent.putExtra("avatar", blogEntry.getAuthorAvatar().toString());
		} else {
			intent.putExtra(
					"avatar",
					"https://github.com/ZhangTingkuo/AndroidCnblogs/blob/master/res/drawable-hdpi/ic_launcher.png");
		}
		intent.putExtra("title", blogEntry.getBlogTitle());
		intent.putExtra("author", blogEntry.getAuthorName());
		intent.putExtra("published",
				AppUtils.parseDateToString(blogEntry.getPublishedDateDate()));
		intent.putExtra("id", blogEntry.getBlogId());
		intent.putExtra("link", blogEntry.getBlogTitle());

		context.startActivity(intent);
	}

	private void viewNewsContent() {
		Intent intent = new Intent(context, NewsActivity.class);
		if (newsEntry.getTopicIcon() != null) {
			intent.putExtra("topicIcon", newsEntry.getTopicIcon().toString());
		} else {
			intent.putExtra(
					"topicIcon",
					"https://github.com/ZhangTingkuo/AndroidCnblogs/blob/master/res/drawable-hdpi/ic_launcher.png");
		}

		intent.putExtra("title", newsEntry.getNewsTitle());
		intent.putExtra("sourceName", newsEntry.getSourceName());
		intent.putExtra("published",
				AppUtils.parseDateToString(newsEntry.getPublishedDate()));
		intent.putExtra("id", newsEntry.getNewsId());

		context.startActivity(intent);
	}

	private void viewAuthor() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				String authorName = blogEntry.getAuthorName();
				String path = Config.SEARCH_AUTHOR_BY_NAME;
				Log.i("SearchActivity", "textAuthor:" + authorName);
				try {
					authorName = URLEncoder.encode(authorName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				path = path.replace("{TERM}", authorName);

				Log.i("SearchActivity", "path:" + path);
				List<User> userList = AppUtils.getUserList(path);
				User userEntry = userList.get(0);

				Intent intent = new Intent(context, UserActivity.class);
				intent.putExtra("blogapp", userEntry.getBlogapp());
				intent.putExtra("link", userEntry.getUserLink().toString());

				if (userEntry.getUserAvatar() != null) {
					intent.putExtra("avatar", userEntry.getUserAvatar()
							.toString());
				} else {
					intent.putExtra(
							"avatar",
							"https://github.com/ZhangTingkuo/AndroidCnblogs/blob/master/res/drawable-hdpi/ic_launcher.png");
				}

				intent.putExtra("postcount", userEntry.getPostCount());
				intent.putExtra("updated",
						AppUtils.parseDateToString(userEntry.getUpdatedDate()));
				intent.putExtra("title", userEntry.getTitle());
				context.startActivity(intent);
			}
		}).start();
	}

	private void addComment(String string, String type) {

	}

	private void viewComment(int id, String type) {
		Intent intent = new Intent(context, CommentActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("type", type);
		context.startActivity(intent);
	}
}

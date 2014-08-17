package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlen.cnblogs.fragment.BlogFragment;
import com.arlen.cnblogs.fragment.NewsFragment;
import com.arlen.cnblogs.fragment.UserFragment;

public class MainActivity extends Activity {
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	private CharSequence drawerTitle;
	private CharSequence actionBarTitle;
	private String[] drawerListItem;
	private int[] drawerListImage = { R.drawable.ic_drawer_list_blog,
			R.drawable.ic_drawer_list_news, R.drawable.ic_drawer_list_user };

	private static boolean isExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// unit menu style
		setOverflowShowAlways();

		actionBarTitle = drawerTitle = getTitle();
		drawerListItem = getResources()
				.getStringArray(R.array.drawer_list_item);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);

		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		drawerList.setAdapter(new DrawerListAdapter(getApplicationContext(),
				drawerListItem, drawerListImage));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		drawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(actionBarTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_search:
			return true;
		case R.id.action_new:
			return true;
		case R.id.action_person:
			return true;
		case R.id.action_collect:
			return true;
		case R.id.action_settings:
			return true;
		case R.id.action_exit:
			System.exit(0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressLint("InflateParams")
	private class DrawerListAdapter extends BaseAdapter {
		private Context context;
		private String[] contenct;
		private int[] images;

		public DrawerListAdapter(Context context, String[] contenct,
				int[] images) {
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

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		Fragment fragment;

		switch (position) {
		case 0:
			fragment = new BlogFragment();
			break;
		case 1:
			fragment = new NewsFragment();
			break;
		case 2:
			fragment = new UserFragment();
			break;
		default:
			fragment = new BlogFragment();
			break;
		}

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		drawerList.setItemChecked(position, true);
		setTitle(drawerListItem[position]);
		drawerLayout.closeDrawer(drawerList);
	}

	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			new ExitHandler().sendEmptyMessageDelayed(0, 1000 * 2);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}

	private static class ExitHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	}

	private void setOverflowShowAlways() {
		try {
			ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
			Field field = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			field.setAccessible(true);
			field.setBoolean(viewConfiguration, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method method = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					method.setAccessible(true);
					method.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		actionBarTitle = title;
		getActionBar().setTitle(actionBarTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

}
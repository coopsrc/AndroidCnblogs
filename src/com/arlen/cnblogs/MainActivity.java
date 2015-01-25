package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arlen.cnblogs.adapter.DrawerListAdapter;
import com.arlen.cnblogs.fragment.BlogFragment;
import com.arlen.cnblogs.fragment.NewsFragment;
import com.arlen.cnblogs.fragment.UserFragment;
import com.arlen.cnblogs.handler.ExitHandler;
import com.arlen.cnblogs.utils.AppConfig;
import com.arlen.cnblogs.utils.DBUtils;

public class MainActivity extends FragmentActivity {
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	private CharSequence drawerTitle;
	private CharSequence actionBarTitle;
	private String[] drawerListItem;
	private int[] drawerListImage = { R.drawable.ic_drawer_list_blog,
			R.drawable.ic_drawer_list_news, R.drawable.ic_drawer_list_user };

	private static boolean isExit;

	private Intent intent;

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

		drawerList.setAdapter(new DrawerListAdapter(this, drawerListItem,
				drawerListImage));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new DrawerListToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}

		// Create Database
		DBUtils.createDB(this);
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
			intent = new Intent(MainActivity.this, SearchActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_new:
			intent = new Intent(MainActivity.this, PublishActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_person:
			
			if (AppConfig.isLogin) {
				intent = new Intent(MainActivity.this, UserCenterActivity.class);
			} else {
				intent = new Intent(MainActivity.this, LoginActivity.class);
				intent.putExtra("from", "MainActivity");
			}
			startActivity(intent);
			return true;
		case R.id.action_collect:
			intent = new Intent(MainActivity.this, CollectionActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_settings:
			intent = new Intent(MainActivity.this, SettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_exit:
			System.exit(0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class DrawerListToggle extends ActionBarDrawerToggle {

		public DrawerListToggle(Activity activity, DrawerLayout drawerLayout,
				int drawerImageRes, int openDrawerContentDescRes,
				int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes,
					openDrawerContentDescRes, closeDrawerContentDescRes);
		}

		@Override
		public void onDrawerClosed(View view) {
			getActionBar().setTitle(actionBarTitle);
			invalidateOptionsMenu();
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			getActionBar().setTitle(drawerTitle);
			invalidateOptionsMenu();
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

		Log.i("MainActivity", "selectItem " + position);
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

		FragmentManager fragmentManager = getSupportFragmentManager();
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
			new ExitHandler(isExit).sendEmptyMessageDelayed(0, 1000 * 2);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
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
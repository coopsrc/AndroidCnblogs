package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private EditText editTextUserName;
	private EditText editTextPassword;
	private Button buttonLogin;
	private Button buttonVisitor;

	private String userName;
	private String password;

	private String cookie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setOverflowShowAlways();

		initComponent();
		setListener();
	}

	private void setListener() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (v.getId() == buttonLogin.getId()) {
					login();
				} else if (v.getId() == buttonVisitor.getId()) {
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}

			}
		};

		buttonLogin.setOnClickListener(listener);
		buttonVisitor.setOnClickListener(listener);
	}
	
	private void login(){
		
	}

	private void initComponent() {
		editTextUserName = (EditText) findViewById(R.id.editTextUserName);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		buttonVisitor = (Button) findViewById(R.id.buttonVisitor);

		// get login info from sharedPreferences
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		editTextUserName.setText(sharedPreferences.getString("userName", ""));
		editTextPassword.setText(sharedPreferences.getString("password", ""));
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

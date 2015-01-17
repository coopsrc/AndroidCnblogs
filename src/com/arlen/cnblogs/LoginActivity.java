package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.arlen.cnblogs.dialog.LoginDialog;
import com.arlen.cnblogs.dialog.LoginDialog.ProgressCallBack;
import com.arlen.cnblogs.task.ImageLoadTask;
import com.arlen.cnblogs.utils.AppMacros;
import com.arlen.cnblogs.utils.HtmlUtils;
import com.arlen.cnblogs.utils.LoginUtils;

public class LoginActivity extends Activity {

	public String from = "SplashActivity";

	private EditText editTextUserName;
	private EditText editTextPassword;
	private EditText editTextCode;
	private Button buttonLogin;
	private Button buttonVisitor;
	private ImageView imageViewCode;

	private String userName;
	private String password;
	private String code = "";

	private Map<String, String> map;
	private String vcid;

	public SharedPreferences sharedPreferences;
	public SharedPreferences.Editor editor;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		sharedPreferences = this.getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();

		setOverflowShowAlways();

		intent = getIntent();
		from = intent.getStringExtra("from");

		initComponent();
		initData();
		setListener();
	}

	private void setListener() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (v.getId() == buttonLogin.getId()) {
					userName = editTextUserName.getText().toString().trim();
					password = editTextPassword.getText().toString().trim();
					code = editTextCode.getText().toString().trim();
					login();
				} else if (v.getId() == buttonVisitor.getId()) {
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				} else if (v.getId() == imageViewCode.getId()) {
					initData();
				}

			}
		};

		buttonLogin.setOnClickListener(listener);
		buttonVisitor.setOnClickListener(listener);
		imageViewCode.setOnClickListener(listener);
	}

	private void initComponent() {
		editTextUserName = (EditText) findViewById(R.id.editTextUserName);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		editTextCode = (EditText) findViewById(R.id.editTextLoginCode);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		buttonVisitor = (Button) findViewById(R.id.buttonVisitor);
		imageViewCode = (ImageView) findViewById(R.id.imageViewLoginCode);

		// get login info from sharedPreferences
		editTextUserName.setText(sharedPreferences.getString("userName",
				""));
		editTextPassword.setText(sharedPreferences.getString("password",
				""));
	}

	private void initData() {
		try {
			final Context context = this;
			new Thread() {
				Handler handler = new Handler(context.getMainLooper()) {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						vcid = map.get("LBD_VCID_c_login_logincaptcha");
						if (vcid != null) {
							editTextCode.setVisibility(View.VISIBLE);
							imageViewCode.setVisibility(View.VISIBLE);
							String path = AppMacros.BotDetectCaptcha + vcid;
							new ImageLoadTask(imageViewCode).execute(path,
									"BotDetectCaptcha");
						}
					}
				};

				public void run() {
					try {
						map = HtmlUtils.getNameParam();
						handler.sendEmptyMessage(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
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

	private void login() {
		LoginDialog dialog = new LoginDialog(this);
		ProgressCallBack callBack = new ProgressCallBack() {

			@Override
			public void action() {
				boolean isLogin = false;
				try {
					if (vcid == null) {
						System.out.println("*** 不 需要  验证码*****");
						isLogin = LoginUtils.login(userName, password, map);
					} else {
						System.out.println("*** 需要 验证码*****");
						isLogin = LoginUtils.login(userName, password, code,
								map);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (isLogin) {
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);

					if (from.equals("SplashActivity")) {
						intent = new Intent(LoginActivity.this,
								MainActivity.class);
					} else if (from.equals("MainActivity")) {
						intent = new Intent(LoginActivity.this,
								UserCenterActivity.class);
					}

					startActivity(intent);
					LoginActivity.this.finish();
					AppMacros.FLAG_LOGIN = true;
				} else {
					initData();
				}
			}

		};
		dialog.showProgressDialog("登录", "正在登录", callBack);
	}
}

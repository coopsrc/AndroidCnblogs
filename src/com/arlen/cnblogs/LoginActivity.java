package com.arlen.cnblogs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
  public Editor editor;

  private Intent intent;

  public static boolean hasLoginInfo = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    setOverflowShowAlways();

    intent = getIntent();
    from = intent.getStringExtra("from");

    initComponent();

    sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
    userName = sharedPreferences.getString("userName", "");
    password = sharedPreferences.getString("password", "");
    if (!userName.equals("") && !password.equals("")) {
      hasLoginInfo = true;
    }

    if (hasLoginInfo) {
      editTextUserName.setVisibility(View.INVISIBLE);
      editTextPassword.setVisibility(View.INVISIBLE);
      buttonLogin.setVisibility(View.GONE);
      buttonVisitor.setVisibility(View.INVISIBLE);
    }

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
          doLogin();
        } else if (v.getId() == buttonVisitor.getId()) {
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
              new ImageLoadTask(imageViewCode).execute(path, "BotDetectCaptcha");
            } else {
              doLogin();
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
      Field field = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
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
          Method method =
              menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
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

  private void doLogin() {
    LoginDialog dialog = new LoginDialog(this);
    ProgressCallBack callBack = new ProgressCallBack() {

      @Override
      public void action() {
        login();
      }

    };
    dialog.showProgressDialog("登录", "正在登录", callBack);
  }

  private void login() {
    if (hasLoginInfo) {
      boolean isLogin = false;
      try {
        if (vcid == null) {
          System.out.println("*** 不 需要  验证码*****");
          isLogin = LoginUtils.login(userName, password, map);
        } else {
          System.out.println("*** 需要 验证码*****");
          isLogin = LoginUtils.login(userName, password, code, map);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (isLogin) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        // Save user name and password
        editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.commit();

        if (from.equals("SplashActivity")) {
          intent = new Intent(LoginActivity.this, MainActivity.class);
        } else if (from.equals("MainActivity")) {
          intent = new Intent(LoginActivity.this, UserCenterActivity.class);
        }

        startActivity(intent);
        LoginActivity.this.finish();
        AppMacros.FLAG_LOGIN = true;
      } else {
        editTextUserName.setVisibility(View.VISIBLE);
        editTextPassword.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.VISIBLE);
        buttonVisitor.setVisibility(View.VISIBLE);
        initData();
      }
    }
  }
}

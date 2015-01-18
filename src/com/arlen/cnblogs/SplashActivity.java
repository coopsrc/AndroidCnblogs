package com.arlen.cnblogs;

import java.util.Timer;
import java.util.TimerTask;

import com.arlen.cnblogs.utils.AppMacros;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class SplashActivity extends Activity {
  public SharedPreferences sharedPreferences;
  private String userName;
  private String password;
  public static boolean hasLoginInfo = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getActionBar().hide();
		final View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(view);
		
	    sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
	    userName = sharedPreferences.getString("userName", "");
	    password = sharedPreferences.getString("password", "");
	    if (!userName.equals("") && !password.equals("")) {
	      hasLoginInfo = true;
	    }

		final Intent intent;
		if (AppMacros.isLogin) {
			intent = new Intent(SplashActivity.this, MainActivity.class);
		} else {
			intent = new Intent(SplashActivity.this, LoginActivity.class);
			intent.putExtra("from", "SplashActivity");
		}
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				startActivity(intent);
				SplashActivity.this.finish();
			}
		};
		timer.schedule(task, 3 * 1000);

		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(2 * 1000);
		view.setAnimation(animation);
	}
}

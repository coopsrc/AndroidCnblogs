package com.arlen.cnblogs;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.arlen.cnblogs.login.Personal;
import com.arlen.cnblogs.utils.AppMacros;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getActionBar().hide();
		final View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(view);

		if (Personal.isLogin(this)) {
			AppMacros.FLAG_LOGIN = true;
		}

		final Intent intent;
		if (AppMacros.FLAG_LOGIN) {
			intent = new Intent(SplashActivity.this, MainActivity.class);
		} else {
			intent = new Intent(SplashActivity.this, LoginActivity.class);
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

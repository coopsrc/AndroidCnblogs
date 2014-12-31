package com.arlen.cnblogs.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class LoginDialog {
	//
	private Context context;
	// 等带对话框的标题
	private String title;
	// 等待内容
	private String message;
	// show
	private final int SHOW = 1;
	// dismiss
	private final int DISMISS = 0;
	// 进度对话框
	private ProgressDialog progressDialog = null;

	public LoginDialog(Context context) {
		this.context = context;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW:
				progressDialog = ProgressDialog.show(context, title, message);
				break;
			case DISMISS:
				progressDialog.dismiss();
				break;
			}
		}
	};

	public void showProgressDialog(String title, String message,
			final ProgressCallBack callBack) {
		this.title = title;
		this.message = message;
		handler.sendEmptyMessage(SHOW);// 弹出对话框
		new Thread() {
			public void run() {
				callBack.action();// 执行操作
				handler.sendEmptyMessage(DISMISS);// 执行完毕，关闭对话框
			}
		}.start();
	}

	public interface ProgressCallBack {
		public void action();
	}
}

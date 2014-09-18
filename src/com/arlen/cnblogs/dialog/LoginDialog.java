package com.arlen.cnblogs.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class LoginDialog {
	//
	private Context mContext;
	// 等带对话框的标题
	private String mTitle;
	// 等待内容
	private String mMessage;
	// show
	private final int SHOW = 1;
	// dismiss
	private final int DISMISS = 0;

	public LoginDialog(Context mContext) {
		this.mContext = mContext;
	}

	// 控制进度对话框的显示与消失
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW:
				progressDialog = ProgressDialog
						.show(mContext, mTitle, mMessage);
				break;
			case DISMISS:
				progressDialog.dismiss();
				break;
			}
		}
	};
	// 进度对话框
	private ProgressDialog progressDialog = null;

	/**
	 * 启用进度对话框
	 * 
	 * @param title
	 *            进度框的标题
	 * @param message
	 *            进度框显示的内容
	 * @param callBack
	 *            回调的执行方法
	 */
	public void showProgressDialog(String title, String message,
			final ProgressCallBack callBack) {
		this.mTitle = title;
		this.mMessage = message;
		handler.sendEmptyMessage(SHOW);// 弹出对话框
		new Thread() {
			public void run() {
				callBack.action();// 执行操作
				handler.sendEmptyMessage(DISMISS);// 执行完毕，关闭对话框
			}
		}.start();
	}

	// 要在进度对话框显示时执行的操作
	public interface ProgressCallBack {
		public void action();
	}
}

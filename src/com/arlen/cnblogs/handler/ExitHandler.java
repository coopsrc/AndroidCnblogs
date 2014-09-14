package com.arlen.cnblogs.handler;

import android.os.Handler;
import android.os.Message;

public class ExitHandler extends Handler  {
	boolean flag = false;

	public ExitHandler(boolean flag) {
		this.flag = flag;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		flag = false;
	}

}

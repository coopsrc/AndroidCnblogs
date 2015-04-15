package com.arlen.cnblogs.task;

import android.content.Context;
import android.os.AsyncTask;

public class LoginTask extends AsyncTask<Void, Void, Void> {
	Context context;

	public LoginTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	

}

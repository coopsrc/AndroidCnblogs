package com.arlen.cnblogs.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class GetMsgTask extends AsyncTask<String, Integer, String> {

	private static final String TAG = GetMsgTask.class.getSimpleName();

	private onTaskCallBack callBack;
	private HttpEntity httpEntity;
	private HttpResponse httpResponse;
	private InputStream inputStream;

	public GetMsgTask(onTaskCallBack callBack) {
		super();
		this.callBack = callBack;
	}

	@Override
	protected String doInBackground(String... params) {

		HttpGet httpGet = new HttpGet(params[0]);
		Log.v(TAG, "Request Url" + params[0]);

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			httpResponse = httpClient.execute(httpGet);
			httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();

			InputStreamReader in = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(in);

			String result;

			result = bufferedReader.readLine();

			if (result != null) {
				return result;
			}

		} catch (Exception e) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return "";
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (!result.equals("")) {
			callBack.onSuccess(result);
		} else {
			callBack.onFailed();
		}
	}

	public static abstract interface onTaskCallBack {
		public abstract void onFailed();

		public abstract void onSuccess(String message);
	}

}

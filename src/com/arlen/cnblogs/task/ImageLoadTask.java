package com.arlen.cnblogs.task;

import com.arlen.cnblogs.utils.AppUtils;
import com.arlen.cnblogs.utils.HttpUtils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<String, Void, Void> {

	private ImageView imageView;
	private Bitmap bitmap;

	public ImageLoadTask(ImageView imageView) {
		super();
		this.imageView = imageView;
	}

	@Override
	protected Void doInBackground(String... params) {
		bitmap = HttpUtils.getBitmap(params[0]);
		if (params[1].equals("avatar")) {
			if (bitmap != null) {
				bitmap = AppUtils.roundCorner(bitmap, 8);
			}
		} else if (params[1].equals("BotDetectCaptcha")) {
			AppUtils.SaveBitmap2File(bitmap, "BotDetectCaptcha.jpg");
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		imageView.setImageBitmap(bitmap);
	}

}

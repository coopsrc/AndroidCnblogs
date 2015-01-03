package com.arlen.cnblogs.test;

import java.util.Map;

import android.test.AndroidTestCase;
import android.util.Log;

import com.arlen.cnblogs.dao.BlogDao;
import com.arlen.cnblogs.dao.DbOpenHelper;
import com.arlen.cnblogs.utils.AppMacros;
import com.arlen.cnblogs.utils.HtmlUtils;
import com.arlen.cnblogs.utils.HttpUtils;
import com.arlen.cnblogs.utils.LoginUtils;

public class CnblogsTest extends AndroidTestCase {

	public CnblogsTest() {

	}

	public void createDB() {
		DbOpenHelper helper = new DbOpenHelper(getContext(),
				AppMacros.DATABASE_NAME, null, AppMacros.DATABASE_VERSION);
		helper.getWritableDatabase();
	}

	public void addDate() {
		BlogDao blogDao = new BlogDao(getContext());
		Object[] params = { 3333, "blog", "blog", "blog", "blog", "blog",
				"blog", "blog", "blog", "blog", 11, 11, 11 };
		blogDao.addData(params);
	}

	public void deleteDate() {
		BlogDao blogDao = new BlogDao(getContext());
		Object[] params = { 2222 };
		blogDao.deleteData(params);
	}

	public void updateDate() {
		BlogDao blogDao = new BlogDao(getContext());
		Object[] params = { 3333, "blog", "blog", "blog", "blog", "blog",
				"blog", "blog", "blog", "blog", 99, 99, 99, 3333 };
		blogDao.updateData(params);
	}

	public void viewData() {
		BlogDao blogDao = new BlogDao(getContext());
		String[] selectionArgs = { "3333" };
		Map<String, String> map = blogDao.viewData(selectionArgs);
		Log.i("viewData", "map --> " + map.get("blogId") + "===" + map.size());
	}

	public void loginTest() throws Exception {
		Map<String, String> map = HtmlUtils.getNameParam();
		String userName = "1757887521@qq.com";
		String password = "zhang1757887521.";

		String vcid = map.get("LBD_VCID_c_login_logincaptcha");

		if (vcid == null) {
			System.out.println("*** 不 需要  验证码*****");
			LoginUtils.login(userName, password, map);
		} else {
			System.out.println("*** 需要 验证码*****");
			String code = "123abC";
			HttpUtils.getBitmap(map.get("LBD_VCID_c_login_logincaptcha"));
			// LoginUtils.login(userName, password, code, map);
		}

	}

	public void getNamrParamTest() {
		try {
			HtmlUtils.getNameParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getPersonInfoTest() {
		try {
			HtmlUtils.getPersonInfo("zhangtingkuo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

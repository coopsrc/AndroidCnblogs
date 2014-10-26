package com.arlen.cnblogs.test;

import java.util.Map;

import com.arlen.cnblogs.dao.BlogDao;
import com.arlen.cnblogs.dao.DbOpenHelper;
import com.arlen.cnblogs.utils.AppMacros;

import android.test.AndroidTestCase;
import android.util.Log;

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
	
	public void viewData(){
		BlogDao blogDao = new BlogDao(getContext());
		String[] selectionArgs = {"3333"};
		Map<String, String> map = blogDao.viewData(selectionArgs);
		Log.i("viewData", "map --> " + map.get("blogId") + "===" + map.size());
	}
	
	public void testList(){
		
	}
}

package com.arlen.cnblogs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arlen.cnblogs.utils.AppMacros;

public class BlogDao implements BaseDao {

	private DbOpenHelper helper = null;

	public BlogDao(Context context) {
		helper = new DbOpenHelper(context, AppMacros.DATABASE_NAME, null,
				AppMacros.DATABASE_VERSION);
	}

	@Override
	public boolean addData(Object[] params) {
		boolean flag = false;
		SQLiteDatabase database = null;

		try {
			String sql = "insert into blog(blogId,blogTitle,blogSummary,publishedDate,updatedDate,authorName,authorUri,authorAvatar,blogLink,blogApp,blogDiggs,blogViews,blogComments) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			database = helper.getWritableDatabase();
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}

		return flag;
	}

	@Override
	public boolean deleteData(Object[] params) {
		boolean flag = false;
		SQLiteDatabase database = null;

		try {
			String sql = "delete from blog where blogId = ? ";
			database = helper.getWritableDatabase();
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}

		return flag;
	}

	@Override
	public boolean updateData(Object[] params) {
		boolean flag = false;
		SQLiteDatabase database = null;

		try {
			String sql = "update blog set blogId = ?, blogTitle = ?, blogSummary = ?, publishedDate = ?, updatedDate = ?, authorName = ?, authorUri = ?, authorAvatar = ?, blogLink = ?, blogApp = ?, blogDiggs = ?, blogViews = ?, blogComments = ? where blogId = ?";
			database = helper.getWritableDatabase();
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}

		return flag;
	}

	@Override
	public boolean cleanData(Object[] params) {
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			String sql1 = "delete from blog";
			String sql2 = "select * from sqlite_sequence";
			String sql3 = "update sqlite_sequence set seq=0 where name = blog";
			database = helper.getWritableDatabase();
			database.execSQL(sql1, params);
			database.execSQL(sql2, params);
			database.execSQL(sql3, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}

		return flag;

	}

	@Override
	public Map<String, String> viewData(String[] selectionArgs) {
		Map<String, String> map = new HashMap<String, String>();
		SQLiteDatabase database = null;
		try {
			String sql = "select * from blog where blogId = ?";
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs);

			while (cursor.moveToNext()) {
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					String key = cursor.getColumnName(i);
					String value = cursor.getString(cursor.getColumnIndex(key));
					if (value == null) {
						value = "";
					}
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			if (database != null) {
				database.close();
			}
		}
		return map;
	}

	@Override
	public List<Map<String, String>> listData(String[] selectionArgs) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		SQLiteDatabase database = null;
		try {
			String sql = "select * from blog";
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs);

			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					String key = cursor.getColumnName(i);
					String value = cursor.getString(cursor.getColumnIndex(key));
					if (value == null) {
						value = "";
					}
					map.put(key, value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}

}

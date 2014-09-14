package com.arlen.cnblogs.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {
	public boolean addData(Object[] params);

	public boolean deleteData(Object[] params);

	public boolean updateData(Object[] params);

	public boolean cleanData(Object[] params);

	public Map<String, String> viewData(String[] selectionArgs);

	public List<Map<String, String>> listData(String[] selectionArgs);
}

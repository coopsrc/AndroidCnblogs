package com.arlen.cnblogs.service;

import java.util.List;
import java.util.Map;

public interface DatabaseService {
	public boolean addData(Object[] params);

	public boolean deleteData(Object[] params);

	public boolean updateData(Object[] params);

	public Map<String, String> viewData(String[] selectionArgs);

	public List<Map<String, String>> listData(String[] selectionArgs);
}

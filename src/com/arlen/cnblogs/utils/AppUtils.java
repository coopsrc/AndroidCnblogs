package com.arlen.cnblogs.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.util.Log;

public class AppUtils {

	/**
	 * 将字符串转换为URL类型
	 * 
	 * @param string
	 * @return URL
	 */
	public static URL parseStringToUrl(String string) {
		URL url = null;
		try {
			Log.i("parseStringToUrl", "转换:" + string);
			url = new URL(string);
			Log.i("parseStringToUrl", "转换成功");
		} catch (MalformedURLException e) {
			Log.e("parseStringToUrl", "转换失败");
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * 将String转换为Date类型
	 * 
	 * @param string
	 * @return Date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseStringToDate(String string) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				AppMacros.SIMPLE_DATA_FORMATE);
		Date date = null;
		try {
			date = dateFormat.parse(string);
		} catch (ParseException e) {
			Log.e("parseStringToDate", "转换失败");
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将Date转换为String类型
	 * 
	 * @param date
	 * @return String
	 */
	@SuppressLint("SimpleDateFormat")
	public static String parseDateToString(Date date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					AppMacros.SIMPLE_DATA_FORMATE);
			return dateFormat.format(date);
		} catch (Exception e) {
			Log.e("parseDateToString", "转换失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将时间转换为中文
	 * 
	 * @param datetime
	 * @return
	 */
	public static String parseDateToChinese(Date datetime) {
		Date today = new Date();
		long seconds = (today.getTime() - datetime.getTime()) / 1000;

		long year = seconds / (24 * 60 * 60 * 30 * 12);// 相差年数
		long month = seconds / (24 * 60 * 60 * 30);// 相差月数
		long date = seconds / (24 * 60 * 60); // 相差的天数
		long hour = (seconds - date * 24 * 60 * 60) / (60 * 60);// 相差的小时数
		long minute = (seconds - date * 24 * 60 * 60 - hour * 60 * 60) / (60);// 相差的分钟数
		long second = (seconds - date * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);// 相差的秒数

		if (year > 0) {
			return year + "年前";
		}
		if (month > 0) {
			return month + "月前";
		}
		if (date > 0) {
			return date + "天前";
		}
		if (hour > 0) {
			return hour + "小时前";
		}
		if (minute > 0) {
			return minute + "分钟前";
		}
		if (second > 0) {
			return second + "秒前";
		}
		return parseDateToString(datetime);
	}

}

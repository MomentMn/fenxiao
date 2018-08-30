package com.hansan.fenxiao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
	public static final String format_1 = "yyyy-MM-dd";
	public static final String format_2 = "yyyy-MM-dd HH:mm:ss";
	
	public static String getDateStr(Date date,String format)
	{
		if(format == null) {
			format = format_1;
		}
		SimpleDateFormat format0 = new SimpleDateFormat(format);  
		return format0.format(date.getTime());
	}
}

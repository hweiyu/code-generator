package com.hwy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 日期工具类
 * @date 2018/8/13 10:10
 **/
public class DateUtils {

    /**
     * 时间格式(yyyy-MM-dd)
     */
	public final static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}

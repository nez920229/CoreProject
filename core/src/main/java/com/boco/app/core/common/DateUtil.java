package com.boco.app.core.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到毫秒的字符串
     * @return 
     */  
    public static String timeStamp2Date(Long seconds,String format) {
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }   
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(seconds));
    }  
    /** 
     * 日期格式字符串转换成时间戳
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static String timeStamp(){  
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);  
        return t;  
    }  

}
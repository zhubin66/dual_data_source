package com.test.testdemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Desc 时间工具类
 */
public class DateUtil {
    public static final String YEAR = "yyyy-MM-dd";
    public static final long ONE_DAY = 24 * 60 * 60 * 1000L;
    public static final long MINUTE_TIME = 60000L;//1分钟
    public static final long HOUR_TIME = 60 * MINUTE_TIME;
    public static final String FULL_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String YEAR_NO_DAY = "yyyy-MM";

    /**
     * 时间格式转化成字符串
     *
     * @param date   时间
     * @param format 格式
     * @return 字符串
     */
    public static String dateToString(Date date, String format) {
        String strDate = null;
        if (date != null && format != null && !"".equals(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            strDate = sdf.format(date);
        }

        return strDate;
    }

    /**
     * 字符串格式转化成日期
     *
     * @param strDate 字符串
     * @param format  格式
     * @return 日期
     */
    public static Date stringToDate(String strDate, String format) {
        Date date = null;
        if (format != null && !"".equals(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            try {
                date = sdf.parse(strDate);
            } catch (Exception var5) {
                date = null;
            }
        }

        return date;
    }

    /**
     * 日期格式化成日期
     *
     * @param date   日期
     * @param format 格式化
     * @return 日期
     */
    public static Date dateToDate(Date date, String format) {
        if (format != null && !"".equals(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                String strDate = sdf.format(date);
                date = sdf.parse(strDate);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
        }

        return date;
    }

    /**
     * 时间推移
     *
     * @param date 时间
     * @param day  推移的天数
     * @return 时间
     */
    public static Date datePass(Date date, Integer day) {
        if (date != null && day != null) {
            long result = date.getTime() + ONE_DAY * day;
            return new Date(result);
        }
        return date;
    }

    /**
     * 日期是否在今天之前
     *
     * @param date 日期
     * @return true/false
     */
    public static boolean isBeforeToday(Date date) {
        boolean flag = false;

        if (date != null && date.before(new Date())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 日期是否是在今天内
     *
     * @param date 日期
     * @return true/false
     */
    public static boolean isInToday(Date date) {
        if (date == null) {
            return false;
        } else {
            boolean flag = false;
            Date now = new Date();
            String nowStr = dateToString(now, YEAR);
            String dateStr = dateToString(date, YEAR);
            if (nowStr.equals(dateStr)) {
                flag = true;
            }

            return flag;
        }
    }


    /**
     * 判断两个时间是否连续
     *
     * @param str1  晚一点的 2019-08-08 01:15
     * @param str2  早一点的 2019-08-08 01:00
     * @param space 是否连续的判断标准
     * @return true/false
     */
    public static Boolean isContinuous(String str1, String str2, long space) {
        if (str1 == null || "".equals(str1) || str2 == null || "".equals(str2)) {
            return false;
        }

        Date date1 = stringToDate(str1, FULL_NO_SECOND);
        Date date2 = stringToDate(str2, FULL_NO_SECOND);

        return Math.abs(date1.getTime() - date2.getTime()) == space;
    }


    //是否在1小时内
    public static Boolean isInHour(Date now) {
        Date dateBegin = dateToDate(now, YEAR);
        return now == null || now.getTime() - dateBegin.getTime() < HOUR_TIME;
    }

}

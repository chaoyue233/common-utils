package com.chaoyue.utils;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
@Slf4j
public class DateUtils {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DAILY_FORMAT = "yyyy-MM-dd";
    public static final String SIMPLE_DAILY_FORMAT = "yyyyMMdd";


    /**
     * 获取当前时间
     *
     * @return Date类型的时间
     */
    public static Date getNow() {
        return new DateTime().toDate();
    }

    /**
     * 获取默认格式化后的当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss 格式字符串当前时间
     */
    public static String getCurrentTimeStr() {
        Date now = new Date();
        SimpleDateFormat sdf_time = new SimpleDateFormat(DEFAULT_FORMAT);
        return sdf_time.format(now);
    }

    /**
     * 根据指定格式转换时间
     *
     * @param date   需要转换的时间
     * @param format 转换的格式
     * @return 转换后的 Date 类型时间
     */
    public static Date formatDate(Date date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = f.parse(f.format(date));
        } catch (ParseException e) {
            log.error("str to date error ", e);
        }
        return result;
    }

    /**
     * 使用默认格式化处理时间
     *
     * @param date 需要处理的时间
     * @return yyyy-MM-dd HH:mm:ss 格式字符串 处理后的时间
     */
    public static String formatDateToStr(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        if (date == null) {
            return null;
        } else {
            return f.format(date);
        }
    }

    /**
     * 使用指定格式转换时间
     *
     * @param date   需要处理的时间
     * @param format 转换格式
     * @return 转换后的 String 格式时间
     */
    public static String formatDateToStr(Date date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        if (date == null) {
            return null;
        } else {
            return f.format(date);
        }
    }

    /**
     * 使用默认格式将字符串转换为时间
     *
     * @param str 需要转换的字符串
     * @return 转换后的 Date 类型时间
     */
    public static Date formatStrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            log.error("str to date error ", e);
        }
        return date;
    }

    /**
     * 使用指定格式将字符串转换为时间
     *
     * @param dateStr 需要转换的字符串
     * @param format  转换格式
     * @return 转换后的 Date 类型时间
     */
    public static Date formatStrToDate(String dateStr, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            log.error("str to date error ", e);
        }
        return date;
    }

    /**
     * 判断时间是否在当前时间之前
     *
     * @param beforeDate 判断的时间
     * @return 是否在当前时间之前
     */
    public static boolean isBeforNowDate(Date beforeDate) {
        Date nowDate = getNow();
        return nowDate.before(beforeDate);

    }

    /**
     * 判断时间是否在当前时间之后
     *
     * @param afterDate 判断的时间
     * @return 是否在当前时间之后
     */
    public static boolean isAfterNowDate(Date afterDate) {
        Date nowDate = getNow();
        return nowDate.after(afterDate);
    }

    /**
     * 获取当前时间的昨天时间
     *
     * @return Date 昨天的时间
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return new DateTime(calendar.getTime()).toDate();
    }

    /**
     * 获取格式转换后的当前时间的昨天时间
     *
     * @param format 转换格式
     * @return Date 昨天的时间
     */
    public static Date getYesterday(String format) {
        return formatDate(getYesterday(), format);
    }

    /**
     * 获取默认格式转换之后的昨天时间
     *
     * @return yyy-MM-dd HH:mm:ss 格式字符串 昨天的时间
     */
    public static String getYesterdayStr() {
        return formatDateToStr(getYesterday());
    }

    /**
     * 获取指定格式转换后的昨天时间
     *
     * @param format 转换格式
     * @return 转换后的昨天时间
     */
    public static String getYesterdayStr(String format) {
        return formatDateToStr(getYesterday(format));
    }

    /**
     * 将秒值转换为时间
     *
     * @param s 秒值
     * @return 时间 如 1小时2分2秒
     */
    public static String getTimeStrBySecond(Long s) {
        if (s == null || s <= 0) {
            return "0小时0分0秒";
        } else {
            Long ms = s * 1000;
            return getTimeStrByMilliSecond(ms);
        }
    }

    /**
     * 将毫秒值转换为时间
     *
     * @param ms 毫秒值
     * @return 时间 如 0天1小时2分2秒
     */
    public static String getTimeStrByMilliSecond(Long ms) {
        if (ms == null || ms <= 0) {
            return "0小时0分0秒";
        }
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("天");
        }
        sb.append(hour).append("小时");
        sb.append(minute).append("分");
        sb.append(second).append("秒");

        return sb.toString();
    }
}

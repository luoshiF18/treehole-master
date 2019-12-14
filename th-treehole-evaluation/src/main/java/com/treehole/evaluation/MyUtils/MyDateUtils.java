package com.treehole.evaluation.MyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @auther: Yan Hao
 */
public final class MyDateUtils {

    /**
     * 日期转字符串,返回固定格式
     *
     * @param date 要转换的date
     * @return
     */
    public static String dateToString1(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = dateFormat.format(date);
        return format;
    }

    /**
     * 日期转字符串，自定义格式
     *
     * @param date   要转换的date
     * @param change 要转换的日期格式，例如：（"yyyy-MM-dd HH:mm"）
     * @return
     */
    public static String dateToString2(Date date, String change) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(change);
        String format = dateFormat.format(date);
        return format;
    }


    /**
     * 字符串转日期,默认格式（用于本工具dateToString1生成的日期字符串,也可以用于属于"yyyy-MM-dd HH:mm"格式的日期）
     *
     * @param simpleDate 要转换的字符串日期，例如（1970-01-01 12:00 ，格式自定义）
     * @return
     * @throws ParseException
     */
    public static Date stringToDate1(String simpleDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = dateFormat.parse(simpleDate);
        return parse;
    }

    /**
     * 字符串转日期,自定义格式
     *
     * @param simpleDate 要转换的字符串日期，例如（1970-01-01 12:00 ，格式自定义）
     * @param change     要转换的日期格式，例如：（"yyyy-MM-dd HH:mm"，格式自定义）
     * @return
     * @throws ParseException
     */
    public static Date stringToDate2(String simpleDate, String change) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(change);
        Date parse = dateFormat.parse(simpleDate);
        return parse;
    }
}

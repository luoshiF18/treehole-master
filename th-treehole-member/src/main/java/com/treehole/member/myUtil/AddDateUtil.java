package com.treehole.member.myUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Shan HuiJie
 * @Description: 得到指定日期加上天数之后的时间
 * @Date 2019.11.21 17:27
 */
public class AddDateUtil {
    /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    Date date = new Date();
    System.out.println(df.format(date));// 当前系统时间        
    Date newDate = stepMonth(date, 1);
    System.out.println("当前时间前1个月的日期：" + df.format(newDate));


    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
    return c.getTime();
    }*/
    public static Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    public static void main(String[] args) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        Date date = dateFormat.parse("2019-11-21 18:11:00"); // 指定日期
        Date da = new Date();
        Date newDate = addDate(date, 365); // 指定日期加上10天
        da = newDate;
        System.out.println(dateFormat.format(date));// 输出格式化后的日期
        System.out.println(dateFormat.format(newDate));
        System.out.println(dateFormat.format(da));

    }


}

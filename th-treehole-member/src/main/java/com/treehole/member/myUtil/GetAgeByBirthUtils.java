package com.treehole.member.myUtil;


import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Shan HuiJie
 * @Description: 根据出生年月计算年龄
 * @Date 2019.11.19 10:29
 */
public class GetAgeByBirthUtils{

    public static int getAgeByBirth(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
/*test*/
//    public static void main(String[] args) throws ParseException {
//        Date time =new Date("Sat Jan 10 22:50:36 CST 1970");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String timeFormat = sdf.format(time);
//        System.out.println(timeFormat);
//
//        Date d2 = new Date();
//         d2.setTime(86400000*365*21);
//        System.out.println(86400000*365*21);
//        System.out.println(d2);
//        int age = getAgeByBirth(d2);
//        System.out.println(age);
//    }
}

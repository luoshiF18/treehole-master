package com.treehole.marketing.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class MyNumberUtils {

    /**
     * 生成指定位数的随机数字
     *
     * @param len
     * @return
     */
    public static String getNumForAssign(int len) {
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, len);
    }

    /**
     * 生成随机不带杠的uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 将一个小数精确到指定位数
     *
     * @param num
     * @param scale
     * @return
     */
    public static double subDecimal(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 从字符串中根据正则表达式寻找，返回找到的数字数组
     *
     * @param value
     * @param regex
     * @return
     */
    public static Double[] searchNumber(String value, String regex) {
        List<Double> doubles = new ArrayList<Double>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            for (int i = 1; i <= result.groupCount(); i++) {
                doubles.add(Double.valueOf(result.group(i)));
            }
        }
        return doubles.toArray(new Double[doubles.size()]);
    }
}


package com.treehole.appointment.MyUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName MyOrderCodeUtils
 * @Description: TODO
 * @Author XDD
 * @Date 2019/12/21 16:39
 **/
public class MyOrderCodeUtils {

    //订单编号前缀
    public static final String PREFIX = "YY";
    //订单编号后缀（核心部分）
    private static long code;

    // 生成订单编号
    public static synchronized String getOrderCode() {
        code++;
        String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        long m = Long.parseLong((str)) * 10000;
        m += code;
        return PREFIX + m;
    }
}

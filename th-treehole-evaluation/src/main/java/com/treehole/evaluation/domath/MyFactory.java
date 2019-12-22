package com.treehole.evaluation.domath;

import com.treehole.evaluation.MyUtils.MyNumberUtils;
import org.apache.commons.math3.stat.StatUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/12/13
 */
public final class MyFactory {
    /**
     * 得出分数
     *
     * @return
     */
    public static Float getScore(List<Double> scores, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        将list转为数组
        double[] doubles = MyNumberUtils.ListTodouble(scores);
//        计算
        Method sum = StatUtils.class.getDeclaredMethod(method, double[].class);
        Double invoke = (Double) sum.invoke(null, doubles);
//        保留三位小数
        DecimalFormat df = new DecimalFormat("0.000");
        return Float.valueOf(df.format(invoke));
    }
}

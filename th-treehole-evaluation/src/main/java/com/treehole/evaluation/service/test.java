package com.treehole.evaluation.service;

import org.apache.commons.math3.stat.StatUtils;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/12/13
 */
public class test {
    @Value("${ev.method}")
    List<String> scoreMethod;

    public static void main(String[] args) {

        /**
         *     - 和|sum
         *     - 平均数|mean
         *     - 方差|variance
         *     - 几何平均数|geometricMean
         *     - 总体方差|populationVariance
         *     - 乘积|product
         *     - 对数求和|sumLog
         *     - 平方和|sumSq
         */
        double[] values = new double[]{0.33, 1.3354, 0.27333, 0.3, 0.501,
                0.444, 0.44, 0.34496, 0.33, 0.3, 0.292, 0.667};
//        Class<?> c1 = Class.forName("org.apache.commons.math3.stat.StatUtils");
        try {
            Method sum = StatUtils.class.getDeclaredMethod("sumSq", double[].class);
            Double invoke = (Double) sum.invoke(null, values);
            DecimalFormat df = new DecimalFormat("0.000");
            System.out.println(Float.valueOf(df.format(invoke)));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

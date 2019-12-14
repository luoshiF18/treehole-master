package com.treehole.evaluation.service;

import com.treehole.evaluation.domath.ScoreProperties;
import org.apache.commons.math3.stat.StatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/12/13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(ScoreProperties.class)
public class TestMath {
    @Autowired
    private ScoreProperties scoreProperties;

    double[] values = new double[]{0.33, 1.33, 0.27333, 0.3, 0.501,
            0.444, 0.44, 0.34496, 0.33, 0.3, 0.292, 0.667};

    @Test
    public void ttt() {
        System.out.println(StatUtils.max(values));
    }

    public void st() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c1 = StatUtils.class;
        Method method = c1.getDeclaredMethod("max");
//        method.setAccessible(true);
        System.out.println(method.invoke(values));
    }

    @Test
    public void ss() {
        List<String> method = scoreProperties.getMethod();
        for (String s : method) {
            System.out.println(s);
        }
    }
}

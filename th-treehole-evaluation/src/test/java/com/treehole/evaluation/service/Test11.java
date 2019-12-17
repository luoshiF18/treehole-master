package com.treehole.evaluation.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @auther: Yan Hao
 * @date: 2019/10/17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test11 {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String TEST_INFO = "test:scaleInfo:";


    @Test
    public void test() {

        Set<Object> keys = redisTemplate.boundHashOps(TEST_INFO + "15422d70f1634c10a25d388d8f88aaa7" + ":" + "1").keys();
        for (Object key : keys) {
            System.out.println(key);
        }
    }

}

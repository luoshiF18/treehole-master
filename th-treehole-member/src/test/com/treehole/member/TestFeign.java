package com.treehole.member;

import com.treehole.framework.domain.marketing.request.ActivityRequest;
import com.treehole.member.client.MemberClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.12.09 21:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFeign {
    @Autowired
    MemberClient memberClient;   //接口代理对象，有feign生成代理对象

    @Test
    public void testFeign(){
        //发起远程调用
        ActivityRequest activityRequest = memberClient.queryActivityAllById("064fd3641e2f419593b350e354c36401");
        System.out.println(activityRequest);
    }
}

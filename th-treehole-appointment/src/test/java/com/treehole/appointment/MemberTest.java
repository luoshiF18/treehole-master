package com.treehole.appointment;

import com.treehole.appointment.client.MemberClient;
import com.treehole.framework.domain.member.Vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberTest {
    @Autowired
    private MemberClient memberClient;

    @Test
    public void getUserVoByUserIdTest(){
        UserVo userVoByUserId = memberClient.getUserVoByUserId("03bc0359134e40bf8bff52df87d84f1e");
        System.out.println(userVoByUserId);
    }
}

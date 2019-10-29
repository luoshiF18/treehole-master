package com.treehole.member.mapper;

import com.treehole.framework.domain.member.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void testQuery(){
        User user1 = new User();
        user1.setUser_name("wangyibo");
        List<User> users = mapper.select(user1);
        System.out.println("users:" + users);
    }
}

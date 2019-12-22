package com.treehole.member;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.mapper.UserVoMapper;
import com.treehole.member.service.UserVoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

<<<<<<<HEAD
        =======
        >>>>>>>master

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

    @Autowired
    private UserVoService userVoService;
    @Autowired
    private UserVoMapper userVoMapper;

    @Test
    public void testQuery1(){
        User user1 = new User();
        user1.setUser_name("wangyibo");
        List<User> users = mapper.select(user1);
        System.out.println("users:" + users);
    }
    @Test
    public void testQuery() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        Date date1 = dateFormat.parse("2019-12-02 00:11:00"); // 指定日期
        Date date2 = dateFormat.parse("2019-12-10 00:11:00"); // 指定日期
        System.out.println(")))))))))))))))"+ date1);
        System.out.println(")))))))))))))))"+ date2);
        //List<User> byTime = userVoMapper.getUserByTime(date1, date2, "1");
        List<UserVo> byTime = userVoService.findAllUserByTime(date1, date2);
        for (UserVo user : byTime){
            System.out.println("++++" + user);
        }
        System.out.println("++++++++++++++" +byTime.size());
    }
}

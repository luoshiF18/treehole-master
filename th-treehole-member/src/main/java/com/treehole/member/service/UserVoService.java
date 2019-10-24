package com.treehole.member.service;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.mapper.UserVoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: uesr扩展类 ：user的能够展示给大家的基本信息
 * @Date 2019.10.21 19:36
 */
@Service
public class UserVoService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserVoMapper userVoMapper;
    @Autowired
    private UserService userService;
    //UserService userService = new UserService();

    /**
     * 查询所有用户Vo信息
     *
     * @param
     * @return List<UserVo>
     */

    public List<UserVo> findAllUserVos() throws Exception {
        List<User> users = userService.findAllUsers();
        List<UserVo> userVos = new ArrayList<UserVo>();
        for(User user:users){
            UserVo uservo = new UserVo();
            uservo.setUniq_id(user.getUniq_id());
            uservo.setUser_image(user.getUser_image());
            uservo.setUser_name(user.getUser_name());
            uservo.setUser_nickname(user.getUser_nickname());
            uservo.setGender(user.getGender());
            uservo.setUser_birth(user.getUser_birth());
            uservo.setUser_email(user.getUser_email());
            uservo.setUser_phone(user.getUser_phone());
            uservo.setUser_qq(user.getUser_qq());
            uservo.setUser_wechat(user.getUser_wechat());
            uservo.setUser_region(user.getUser_region());
            uservo.setUser_createtime(user.getUser_createtime());

            userVos.add(uservo);
        }
        //BeanUtils.copyProperties(userExts,users);
        return userVos;
    }

    /**
     * 通过uniq_id查询用户拓展类
     * @return List<UserVo>
     */
    public List<UserVo> getUserByUniqId(String uniq_id) throws Exception{
        User user1 = new User();
        user1.setUniq_id(uniq_id);
        List<User> users = userMapper.select(user1);

        List<UserVo> userVos = new ArrayList<UserVo>();
        for(User user:users){
            UserVo uservo = new UserVo();
            uservo.setUniq_id(user.getUniq_id());
            uservo.setUser_image(user.getUser_image());
            uservo.setUser_name(user.getUser_name());
            uservo.setUser_nickname(user.getUser_nickname());
            uservo.setGender(user.getGender());
            uservo.setUser_birth(user.getUser_birth());
            uservo.setUser_email(user.getUser_email());
            uservo.setUser_phone(user.getUser_phone());
            uservo.setUser_qq(user.getUser_qq());
            uservo.setUser_wechat(user.getUser_wechat());
            uservo.setUser_region(user.getUser_region());
            uservo.setUser_createtime(user.getUser_createtime());

            userVos.add(uservo);
        }
        return userVos;
    }

    /**
     * 通过user_id查询用户拓展类
     * @return List<UserVo>
     */
    public List<UserVo> getUserByUserId(String user_id) throws Exception{

        List<User> users = userService.getUserById(user_id);

        List<UserVo> userVos = new ArrayList<UserVo>();
        for(User user:users){
            UserVo uservo = new UserVo();
            uservo.setUniq_id(user.getUniq_id());
            uservo.setUser_image(user.getUser_image());
            uservo.setUser_name(user.getUser_name());
            uservo.setUser_nickname(user.getUser_nickname());
            uservo.setGender(user.getGender());
            uservo.setUser_birth(user.getUser_birth());
            uservo.setUser_email(user.getUser_email());
            uservo.setUser_phone(user.getUser_phone());
            uservo.setUser_qq(user.getUser_qq());
            uservo.setUser_wechat(user.getUser_wechat());
            uservo.setUser_region(user.getUser_region());
            uservo.setUser_createtime(user.getUser_createtime());
            userVos.add(uservo);
        }
        return userVos;
    }

    /**
     * 通过user_phone查询用户拓展类信息
     * @return List<UserVo>
     */
    public UserVo getUserByUserPhone(String user_phone) throws Exception{

        //System.out.println("787777777777777777777777777778878787");
        User user = userService.findUserByPhone(user_phone);
        //System.out.println("++++++++++++++++++++++++" + user);
        UserVo uservo = new UserVo();
        uservo.setUniq_id(user.getUniq_id());
        uservo.setUser_image(user.getUser_image());
        uservo.setUser_name(user.getUser_name());
        uservo.setUser_nickname(user.getUser_nickname());
        uservo.setGender(user.getGender());
        uservo.setUser_birth(user.getUser_birth());
        uservo.setUser_email(user.getUser_email());
        uservo.setUser_phone(user.getUser_phone());
        uservo.setUser_qq(user.getUser_qq());
        uservo.setUser_wechat(user.getUser_wechat());
        uservo.setUser_region(user.getUser_region());
        uservo.setUser_createtime(user.getUser_createtime());

        return uservo;
    }





}

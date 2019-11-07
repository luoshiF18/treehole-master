package com.treehole.member.service;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyNumberUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户
     *
     * @param
     * @return List<User>
     */

    public List<User> findAllUsers() throws Exception {
        List<User> users = userMapper.selectAll();
        return users;
    }
    /**
     * 查询所有用户Vo信息
     *
     * @param
     * @return List<UserVo>
     */

    public List<UserVo> findAllUserVos() throws Exception {
        List<User> users = userMapper.selectAll();
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

            userVos.add(uservo);
        }
        //BeanUtils.copyProperties(userExts,users);
        return userVos;
    }

    /**
     * 根据user对象查询所有user记录
     *
     * @param
     * @return List<UserExt>
     */
    public User findUser(User user) throws Exception {

        return userMapper.selectOne(user);
    }

    /**
     * 根据user_iphone查询所有user记录
     *
     * @param phonenumber
     * @return User
     */
    public User findUserByPhone(String phonenumber) throws Exception {
        User user = new User();
        //System.out.println("pppppppppppp"+phonenumber);
        user.setUser_phone(phonenumber);
        return userMapper.selectOne(user);
    }


    /**
     * 通过id查询用户
     * @return List<User>
     */
    public List<User> getUserById(String user_id) throws Exception{
        User user = new User();
        user.setUser_id(user_id);
        List<User> users = userMapper.select(user);
        return users;
    }

    /**
     * 通过uniq_id查询用户拓展类
     * @return List<User>
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
            userVos.add(uservo);
        }
        return userVos;
    }

    /**
     * 通过user_id查询用户拓展类
     * @return List<User>
     */
    public List<UserVo> getUserByUserId(String user_id) throws Exception{
        User user1 = new User();
        user1.setUser_id(user_id);
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
            userVos.add(uservo);
        }
        return userVos;
    }




    /**
     * 通过id删除用户
     * @param user_id
     * @return
     */
    public int deleteUserById(String user_id) throws Exception{
        User user = new User();
        user.setUser_id(user_id);
        int del = userMapper.delete(user);
        return  del;
    }

    /**
     * 创建一条用户信息
     *
     * @param user
     * @return int
     */
    public int insertUser(User user) throws Exception{

        user.setUser_id(MyNumberUtils.getUUID());
        user.setUniq_id(MyNumberUtils.getNumForAssign(6));
        //将密码MD5加密！！！！未实现
        user.setUser_createtime(new Date());
        //System.out.println("+++++++++"+ user.getUniq_id());
        user.setPoints_now(0);
        int ins = userMapper.insert(user);
        return ins;
    }

    /**
     * 更新用户基本信息
     *
     * @param user
     * @return int
     */
    public int updateUser(User user) throws Exception{

        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id",user.getUser_id());
        int upd= userMapper.updateByExampleSelective(user,example);
        //System.out.println("11111111"+user);
        //System.out.println("22222222" +upd);
        return upd;
    }


    /*public int updatePhone(User user) {
        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id",user.getUser_id());
    }*/
}

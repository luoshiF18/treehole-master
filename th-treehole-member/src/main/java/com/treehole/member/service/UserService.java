package com.treehole.member.service;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyMd5Utils;
import com.treehole.member.myUtil.MyNumberUtils;

import org.apache.commons.lang.StringUtils;
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

    public List<User> findAllUsers() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    /**
     * 根据user对象查询所有user记录
     *
     * @param
     * @return List<UserVo>
     */
    public User findUser(User user) {

        return userMapper.selectOne(user);
    }

    /**
     * 根据user_iphone查询所有user记录
     *
     * @param phonenumber
     * @return User
     */
    public User findUserByPhone(String phonenumber)  {
        User user = new User();
        //System.out.println("pppppppppppp"+phonenumber);
        user.setUser_phone(phonenumber);
        return userMapper.selectOne(user);
    }


    /**
     * 通过id查询用户
     * @return List<User>
     */
    public User getUserById(String user_id){
        User user = new User();
        user.setUser_id(user_id);
        User users = userMapper.selectOne(user);
        if(user == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        return users;
    }

    /**
     * 通过id删除用户
     * @param user_id
     * @return
     */
    public int deleteUserById(String user_id) {
        //System.out.println("111111111111111111"+user_id);
        if(StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
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
    public void insertUser(User user)  {
        //System.out.println("--------------------"+user);
        user.setUser_id(MyNumberUtils.getUUID());
        user.setUniq_id(MyNumberUtils.getNumForAssign(6));
        //将密码MD5加密！！！！
        String pw=user.getPassword();
        try {
            user.setPassword(MyMd5Utils.getMd5(pw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setUser_createtime(new Date());
        user.setPoints_now(0);
        if(userMapper.insert(user) != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

    }

    /**
     * 更新用户基本信息
     *
     * @param user
     * @return int
     */
    public int updateUser(User user){

        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id",user.getUser_id());
        int upd= userMapper.updateByExampleSelective(user,example);
        //System.out.println("11111111"+user);
        //System.out.println("22222222" +upd);
        return upd;
    }

    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}

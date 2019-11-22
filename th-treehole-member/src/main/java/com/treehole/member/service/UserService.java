package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyMd5Utils;
import com.treehole.member.myUtil.MyNumberUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private CheckinService checkinService;


    /**
     * 根据user对象查询所有user记录
     *
     * @param
     * @return List<UserVo>
     */
    public User findUser(User user) {
        User user1 = userMapper.selectOne(user);
        if(user1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return user1;
    }

    /**
     * 根据user_nickname查询所有user记录
     *
     * @param
     * @return User
     */
    public User findUserByNickname(String nickname){
        if(StringUtils.isBlank(nickname)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        User user = new User();
        user.setUser_nickname(nickname);
        User use = userMapper.selectOne(user);

        return use;
    }

    /**
     * 根据user_iphone查询所有user记录
     *
     * @param phonenumber
     * @return User
     */
    public User findUserByPhone(String phonenumber)  {
        User user = new User();
        user.setUser_phone(phonenumber);
        return  userMapper.selectOne(user);
    }


    /**
     * 通过id查询用户
     * @return List<User>
     */
    public User getUserById(String user_id){
        User user = new User();
        user.setUser_id(user_id);
        User users = userMapper.selectOne(user);
        if(users == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        return users;
    }
    /**
     * 通过id删除用户
     * @param user_id
     * @return
     */
    @Transactional
    public void deleteUserById(String user_id) {
        //id不为空
        if(StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //用户不为空
        if(this.getUserById(user_id) != null){
            User user = new User();
            user.setUser_id(user_id);
            //会员卡信息删除
            cardsService.deleteCard(user_id);

            //用户信息删除
            int del = userMapper.delete(user);
            if( del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }

        }else{
            ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
        }

    }

    /**
     * 创建一条用户信息
     *
     * @param user
     * @return int
     */
    @Transactional
    public void insertUser(User user)  {
        if(user == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        user.setUser_id(MyNumberUtils.getUUID());
        //将密码MD5加密！！！！
        String pw=user.getPassword();
        try {
            user.setPassword(MyMd5Utils.getMd5(pw));
        } catch (Exception e) {
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        //昵称唯一性！！
        String nickname1 = user.getUser_nickname();
        /*if(this.findUserByNickname(nickname1) != null){
           // ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
            Random random = new Random();
            String nickname2 = nickname1 + random.nextInt(1000);
            //怎样返回给前端？
        }
        if(this.findUserByNickname(nickname2) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/
        if(this.findUserByNickname(nickname1) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }
        user.setRole_id(user.getRole_id());
        user.setUser_nickname(nickname1);
        user.setUser_createtime(new Date());
        user.setUser_type(user.getUser_type());
        user.setUser_status(0);  //默认正常状态
        //用户注册，角色已经默认为1，普通用户 前端按钮携带传入role的值

        int ins = userMapper.insert(user);
        //会员卡表内新增数据
        cardsService.insertCard(user.getUser_id());
        //
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        //往cards表中插入数据
       //cardsService.insertCard(user);
    }

    /**
     * 更新用户基本信息  手机号除外
     *
     *
     * @param user
     * @return int
     */
    public void updateUser(User user){

        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id",user.getUser_id());
        //昵称唯一
        String nickname = user.getUser_nickname();
        if(this.findUserByNickname(nickname) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }
        //密码加密
        String pw = user.getPassword();
        try {
            user.setPassword(MyMd5Utils.getMd5(pw));
        } catch (Exception e) {
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        //
        int upd= userMapper.updateByExampleSelective(user,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }
    /*
    * 注册方法 registerUser()
    * */


    /*
    * 登录方法 isLoginUser()
    * */

    public boolean isLoginUser(User user){
        //得到一个集合
        ArrayList<User> arrList = new ArrayList<User>();

        boolean flag = false;

        for(User user1 : arrList){
            if(user1 != null){
                String pw = null;
                try {
                    pw = MyMd5Utils.getMd5(user.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(user1.getUser_nickname().equals(user.getUser_nickname())&&user1.getPassword().equals(pw)){
                    flag = true;
                }
            }
        }
        return flag;
    }
    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}

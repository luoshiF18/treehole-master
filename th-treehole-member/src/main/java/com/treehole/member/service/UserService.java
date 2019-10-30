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

    /**
     * 查询所有用户
     *
     * @param
     * @return List<User>
     */

    public QueryResult findAllUsers(Integer page, Integer size) {
        //        分页
        PageHelper.startPage(page, size);
       //查询
        List<User> users = userMapper.selectAll();
        if (CollectionUtils.isEmpty(users)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<User> pageInfo = new PageInfo<>(users);

        return new QueryResult(users, pageInfo.getTotal());

    }

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
    public void deleteUserById(String user_id) {
        //System.out.println("111111111111111111"+user_id);
        if(StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        User user = new User();
        user.setUser_id(user_id);
        int del = userMapper.delete(user);
        if(this.getUserById(user_id) != null){
            ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
        }
        if( del != 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }

    }

    /**
     * 创建一条用户信息
     *
     * @param user
     * @return int
     */
    public void insertUser(User user)  {
        user.setUser_id(MyNumberUtils.getUUID());
        //将密码MD5加密！！！！
        String pw=user.getPassword();
        try {
            user.setPassword(MyMd5Utils.getMd5(pw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String nickname1 = user.getUser_nickname();
        /*if(this.findUserByNickname(nickname1) != null){
           // ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
            Random random = new Random();
            String nickname2 = nickname1 + random.nextInt(1000);
        }
        if(this.findUserByNickname(nickname2) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/
        if(this.findUserByNickname(nickname1) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }
        user.setUser_nickname(nickname1);

        user.setUser_createtime(new Date());
        //
        int ins = userMapper.insert(user);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        //往cards表中插入数据
       //cardsService.insertCard(user);
    }

    /**
     * 更新用户基本信息
     *
     *
     * @param user
     * @return int
     */
    public void updateUser(User user){

        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id",user.getUser_id());
        //昵称
        /*String nickname = user.getUser_nickname();
        if(this.findUserByNickname(nickname) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/
        int upd= userMapper.updateByExampleSelective(user,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }

    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}

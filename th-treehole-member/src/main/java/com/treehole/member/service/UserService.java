package com.treehole.member.service;

import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.member.mapper.ThMenuMapper;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import com.treehole.member.myUtil.MyPassword;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    @Autowired
    private ThMenuMapper thMenuMapper;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private CheckinService checkinService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PointService pointService;

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

    public UserExt getUserExt(String userNickName){
        if (StringUtils.isBlank(userNickName)){
            ExceptionCast.cast( MemberCode.DATA_ERROR);
        }
        User user = new User();
        user.setUser_nickname(userNickName);
        User use = userMapper.selectOne(user);
        //查询用户所有权限
        List<ThMenu> thMenus = thMenuMapper.selectPermissionByUserId(use.getUser_id());
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(use,userExt);
        //设置权限
        userExt.setPerimissions(thMenus);
        return userExt;
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

    public User findUserByRolePhone(String phonenumber,String roleid)  {
        User user = new User();
        user.setUser_phone(phonenumber);
        user.setRole_id(roleid);
        User user1 = userMapper.selectOne(user);
        return user1;

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
    //@CacheEvict(value="MemberUser",allEntries=true)
    @Transactional
    public void deleteUserById(String user_id) {
        //id不为空
        if(StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
             //用户不为空
             User user = this.getUserById(user_id);
            //role:会员 ->会员卡  内含（积分/签到）信息删除
            if((user.getRole_id()).equals("1") ) {
                cardsService.deleteCard(user_id);
            }
            //用户信息删除
            int del = userMapper.delete(user);
            if( del != 1) {
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }

    }

    /**
     * 创建一条用户信息
     *
     * @param user
     * @return void
     */
    //@CacheEvict(value="MemberUser",allEntries=true)
    //@Transactional
    public void insertUser(User user)  {
        if(user == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        user.setUser_id(MyNumberUtils.getUUID());
        //将密码加密加盐 spring-security！！！！
        String pw=user.getPassword();
        try {
            user.setPassword(MyPassword.PasswrodEncoder(pw));
        } catch (Exception e) {
            ExceptionCast.cast(MemberCode.PASSWORD_ERROR);
        }
        //昵称唯一性！！
        String nickname1 = user.getUser_nickname();
        if(this.findUserByNickname(nickname1) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }else {
            user.setUser_nickname(nickname1);
        }
        //手机号唯一性  角色不同的同一手机号依旧能注册
        if(this.findUserByRolePhone(user.getUser_phone(),user.getRole_id()) == null){
            user.setUser_phone(user.getUser_phone());
        }else{
            ExceptionCast.cast(MemberCode.PHONE_IS_EXIST);
        }
        if(user.getUser_birth() == null){
            user.setUser_birth(new Date());
        }
        user.setUser_createtime(new Date());
        user.setUser_status(0);  //默认正常状态
        if(user.getRole_id().equals("1")) {  //1普通会员
            //会员卡表内新增数据
            cardsService.insertCard(user.getUser_id());
        }
        //用户注册，角色已经默认为1，普通用户 前端按钮携带传入role的值
        int ins = userMapper.insert(user);
        //
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

    }

    /**
     * 更新用户基本信息  手机号、密码除外
     * @param uservo
     * @return int
     */
    @Transactional
    //@CacheEvict(value="MemberUser",allEntries=true)
    public void updateUser(UserVo uservo){
        User user = new User();
        //user.setUser_id(uservo.getUser_id());
        User user1 = this.getUserById(uservo.getUser_id());
        //Role role = new Role();
       // role.setRole_name(uservo.getRole_name());
        //String roleId = roleService.findRoleByRole(role).getRole_id();  //获取角色id
        if(StringUtils.isNotEmpty(uservo.getUser_phone()) && !(uservo.getUser_phone()).equals(user1.getUser_phone())){
            /*修改手机号*/
            if(this.findUserByRolePhone(uservo.getUser_phone(),user1.getRole_id()) == null){
                user.setUser_phone(uservo.getUser_phone());
            }else{
                ExceptionCast.cast(MemberCode.PHONE_IS_EXIST);
            }
        }
        if(StringUtils.isNotEmpty(uservo.getUser_nickname())&& !(uservo.getUser_nickname()).equals(user1.getUser_nickname())){
            /*修改昵称*/
            if(this.findUserByNickname(uservo.getUser_nickname()) == null){
                user.setUser_nickname(uservo.getUser_nickname());
            }else{
                ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
            }
        }
        user.setUser_id(uservo.getUser_id());
        user.setUser_name(uservo.getUser_name());
        if(StringUtils.isNotEmpty(uservo.getGender())){
            user.setGender(uservo.getGender().equals("男") ? 0:1);
        }

        user.setUser_birth(uservo.getUser_birth());
        if(StringUtils.isNotEmpty(uservo.getUser_status())){
            user.setUser_status(uservo.getUser_status().equals("正常") ? 0:1);
        }
       if(StringUtils.isNotEmpty(uservo.getUser_type())){
           user.setUser_type(uservo.getUser_type().equals("个人")?0:1);
       }
        user.setUser_email(uservo.getUser_email());
        user.setUser_qq(uservo.getUser_qq());
        user.setUser_wechat(uservo.getUser_wechat());
        user.setUser_region(uservo.getUser_region());
        user.setCompany_id(uservo.getCompany_id());   //!!判断公司id是否存在 未完成
        //System.out.println("|||||||"+ user);
        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id",user.getUser_id());
        int upd= userMapper.updateByExampleSelective(user,example);   //修改的部分值组成的对象
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }
    /*
    * 登录方法 isLoginUser()
    *
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
 */
    /*更改密码 */
    @Transactional
    //@CacheEvict(value="MemberUser",allEntries=true)
    public void updatePass(String id,String OldPass,String NewPass){
        User user = this.getUserById(id);
        //判断旧密码
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean f = passwordEncoder.matches(OldPass, user.getPassword());
        if(f){
            String newPa = null;
            try {
                newPa = MyPassword.PasswrodEncoder(NewPass);
            } catch (Exception e) {
                ExceptionCast.cast(MemberCode.PASSWORD_ERROR);
            }
            user.setPassword(newPa);
            Example example =new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("user_id",id);
            int upd= userMapper.updateByExampleSelective(user,example);
            if(upd != 1){
                ExceptionCast.cast(MemberCode.UPDATE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.PASSWORD_OLD_ERROR);
        }

    }

    /*更改手机号
    * 手机号 不用角色id，有user_id就行
    * */
    @Transactional
    //@CacheEvict(value="MemberUser",allEntries=true)
    public void updatePhone(User user){
        if(this.findUserByPhone(user.getUser_phone()) == null){
            user.setUser_phone(user.getUser_phone());
        }else{
            ExceptionCast.cast(MemberCode.PHONE_IS_EXIST);
        }
        Example example =new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id",user.getUser_id());
        int upd= userMapper.updateByExampleSelective(user,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }
    /*忘记密码 未实现*/

}

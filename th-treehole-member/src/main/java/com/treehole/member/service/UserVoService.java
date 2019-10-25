package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.RoleMapper;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.mapper.UserVoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private RoleMapper roleMapper;
    //UserService userService = new UserService();

    /**
     * 查询所有用户Vo信息
     *
     * @param
     * @return List<UserVo>
     */

    public QueryResult findAllUserVos(Integer page, Integer size) {
        //        分页
        //PageHelper.startPage(page, size);
        Page pag =PageHelper.startPage(page,size);
        //查询
        List<User> users = userMapper.selectAll();
        if (CollectionUtils.isEmpty(users)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        List<UserVo> userVos = new ArrayList<UserVo>();
        for(User user:users){
            UserVo uservo = new UserVo();
            String roleId=user.getRole_id();
            Role role = new Role();
            role.setRole_id(roleId);
            //uservo.setUniq_id(user.getUniq_id());
            uservo.setUser_id(user.getUser_id());
            uservo.setRole_name(roleMapper.selectOne(role).getRole_name());
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
            uservo.setCompany_id(user.getCompany_id());
            userVos.add(uservo);
        }
        //解析分页结果
        PageInfo<UserVo> pageInfo = new PageInfo<>(pag.getResult());
        return  new QueryResult(userVos, pageInfo.getTotal());
    }

    /**
     * 通过uniq_id查询用户拓展类
     * @return List<UserVo>
     */
    /*public UserVo getUserByUniqId(String uniq_id) {
        User user1 = new User();
        user1.setUniq_id(uniq_id);
        User user = userMapper.selectOne(user1);
        if(user == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        String roleId=user.getRole_id();
        Role role = new Role();
        role.setRole_id(roleId);
        //System.out.println("++++++++++++++++++++++"+ roleId);
        UserVo uservo = new UserVo();
        uservo.setUniq_id(user.getUniq_id());
        uservo.setRole_name(roleMapper.selectOne(role).getRole_name());
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
        uservo.setCompany_id(user.getCompany_id());
        return uservo;
    }*/

    /**
     * 通过user_id查询用户拓展类
     * @return List<UserVo>
     */
    public UserVo getUserByUserId(String user_id) {

        User user = userService.getUserById(user_id);
        if(user == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        String roleId=user.getRole_id();
        Role role = new Role();
        role.setRole_id(roleId);
        UserVo uservo = new UserVo();
        uservo.setUser_id(user.getUser_id());
        uservo.setRole_name(roleMapper.selectOne(role).getRole_name());
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
        uservo.setCompany_id(user.getCompany_id());
        return uservo;
    }

    /**
     * 通过user_phone查询用户拓展类信息
     * @return List<UserVo>
     */
    public UserVo getUserByUserPhone(String user_phone) {

        User user = userService.findUserByPhone(user_phone);
        if(user == null){
            ExceptionCast.cast(MemberCode.PHONE_NOT_EXIST);
        }
        String roleId=user.getRole_id();
        Role role = new Role();
        role.setRole_id(roleId);
        UserVo uservo = new UserVo();
        uservo.setUser_id(user.getUser_id());
        uservo.setRole_name(roleMapper.selectOne(role).getRole_name());
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
        uservo.setCompany_id(user.getCompany_id());

        return uservo;
    }


    /**
     * 通过user_phone查询用户拓展类信息
     * @return List<UserVo>
     */
    public UserVo getUserByNickname(String nickname) {
        User user = userService.findUserByNickname(nickname);
        if(user == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        String roleId=user.getRole_id();
        Role role = new Role();
        role.setRole_id(roleId);
        UserVo uservo = new UserVo();
        uservo.setUser_id(user.getUser_id());
        uservo.setRole_name(roleMapper.selectOne(role).getRole_name());
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
        uservo.setCompany_id(user.getCompany_id());

        return uservo;
    }




}

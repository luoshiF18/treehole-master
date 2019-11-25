package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.RoleMapper;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.mapper.UserVoMapper;
import com.treehole.member.myUtil.GetAgeByBirthUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
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

    /**
     * 查询所有用户Vo信息
     * 自定义条件查询id/昵称/手机号码
     */
    public QueryResponseResult findAllUserVos(Integer page,
                                              Integer size,
                                              UserListRequest userListRequest) {
        //分页
        Page pag =PageHelper.startPage(page,size);
        //判断请求条件的合法性
        if (userListRequest == null){
            userListRequest = new UserListRequest();
        }
        //        把字节码传给example，就可以通过反射获取数据库信息
        Example example = new Example(User.class);
        //User user1 = new User();
        //判断不为空字符串
        if(StringUtils.isNotEmpty(userListRequest.getUser_id())){
           // user1.setUser_id(userListRequest.getUser_id());
            //过滤条件
            example.createCriteria().andLike("user_id", "%" + userListRequest.getUser_id() + "%");
        }
        if(StringUtils.isNotEmpty(userListRequest.getUser_nickname())){
           // user1.setUser_nickname(userListRequest.getUser_nickname());
            example.createCriteria().andLike("user_nickname", "%" + userListRequest.getUser_nickname() + "%");
        }
        if(StringUtils.isNotEmpty(userListRequest.getUser_phone())){
           // user1.setUser_phone(userListRequest.getUser_phone());
            example.createCriteria().andLike("user_phone", "%" + userListRequest.getUser_phone() + "%");
        }
        //查询
        //List<User> users = userMapper.select(user1);
        List<User> users = userMapper.selectByExample(example);

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
            uservo.setGender(user.getGender() == 0 ? "男":"女");
            if(user.getUser_birth() == null){
                user.setUser_birth(null);
            }else{
            try {
                uservo.setAge(GetAgeByBirthUtils.getAgeByBirth(user.getUser_birth()));
            } catch (ParseException e) {
                ExceptionCast.cast(MemberCode.BIRTH_ERROR);
            }
            }
            uservo.setUser_birth(user.getUser_birth());
            uservo.setUser_email(user.getUser_email());
            uservo.setUser_phone(user.getUser_phone());
            uservo.setUser_qq(user.getUser_qq());
            uservo.setUser_wechat(user.getUser_wechat());
            uservo.setUser_region(user.getUser_region());
            uservo.setUser_createtime(user.getUser_createtime());
            uservo.setCompany_id(user.getCompany_id());
            //显示user类型
            uservo.setUser_type(user.getUser_type() == 0 ? "个人":"企业");
            //显示user状态
            uservo.setUser_status(user.getUser_status() == 0 ? "正常":"禁止");
            userVos.add(uservo);
        }
        //解析分页结果
        PageInfo<UserVo> pageInfo = new PageInfo<>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(userVos);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);

    }



    /**
     * 通过user_id查询用户拓展类
     * @return List<UserVo>
     */
    public UserVo getUserByUserId(String user_id) {

        User user = userService.getUserById(user_id);
        /*if(user == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }*/
        String roleId=user.getRole_id();
        Role role = new Role();
        role.setRole_id(roleId);
        UserVo uservo = new UserVo();
        uservo.setUser_id(user.getUser_id());
        uservo.setRole_name(roleMapper.selectOne(role).getRole_name());
        uservo.setUser_image(user.getUser_image());
        uservo.setUser_name(user.getUser_name());
        uservo.setUser_nickname(user.getUser_nickname());
        uservo.setGender(user.getGender() == 0 ? "男":"女");
        if(user.getUser_birth() == null){
            user.setUser_birth(null);
        }else{
            try {
                uservo.setAge(GetAgeByBirthUtils.getAgeByBirth(user.getUser_birth()));
            } catch (ParseException e) {
                ExceptionCast.cast(MemberCode.BIRTH_ERROR);
            }
        }
        uservo.setUser_birth(user.getUser_birth());
        uservo.setUser_email(user.getUser_email());
        uservo.setUser_phone(user.getUser_phone());
        uservo.setUser_qq(user.getUser_qq());
        uservo.setUser_wechat(user.getUser_wechat());
        uservo.setUser_region(user.getUser_region());
        uservo.setUser_createtime(user.getUser_createtime());
        uservo.setCompany_id(user.getCompany_id());
        //显示user类型
        uservo.setUser_type(user.getUser_type() == 0 ? "个人":"企业");
        //显示user状态
        uservo.setUser_status(user.getUser_status() == 0 ? "正常":"禁止");
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
        uservo.setGender(user.getGender() == 0 ? "男":"女");
        if(user.getUser_birth() == null){
            user.setUser_birth(null);
        }else{
            try {
                uservo.setAge(GetAgeByBirthUtils.getAgeByBirth(user.getUser_birth()));
            } catch (ParseException e) {
                ExceptionCast.cast(MemberCode.BIRTH_ERROR);
            }
        }
        uservo.setUser_birth(user.getUser_birth());
        uservo.setUser_email(user.getUser_email());
        uservo.setUser_phone(user.getUser_phone());
        uservo.setUser_qq(user.getUser_qq());
        uservo.setUser_wechat(user.getUser_wechat());
        uservo.setUser_region(user.getUser_region());
        uservo.setUser_createtime(user.getUser_createtime());
        uservo.setCompany_id(user.getCompany_id());
        //显示user类型
        uservo.setUser_type(user.getUser_type() == 0 ? "个人":"企业");
        //显示user状态
        uservo.setUser_status(user.getUser_status() == 0 ? "正常":"禁止");
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
        uservo.setGender(user.getGender() == 0 ? "男":"女");
        if(user.getUser_birth() == null){
            user.setUser_birth(null);
        }else{
            try {
                uservo.setAge(GetAgeByBirthUtils.getAgeByBirth(user.getUser_birth()));
            } catch (ParseException e) {
                ExceptionCast.cast(MemberCode.BIRTH_ERROR);
            }
        }
        uservo.setUser_birth(user.getUser_birth());
        uservo.setUser_email(user.getUser_email());
        uservo.setUser_phone(user.getUser_phone());
        uservo.setUser_qq(user.getUser_qq());
        uservo.setUser_wechat(user.getUser_wechat());
        uservo.setUser_region(user.getUser_region());
        uservo.setUser_createtime(user.getUser_createtime());
        uservo.setCompany_id(user.getCompany_id());
        //显示user类型
        uservo.setUser_type(user.getUser_type() == 0 ? "个人":"企业");
        //显示user状态
        uservo.setUser_status(user.getUser_status() == 0 ? "正常":"禁止");
        return uservo;
    }

    /*
    * 参数：nickname list集合
    * 返回：对象的集合
    * */
    public List<UserVo> getUserByNicknames(List<String> nicknames){
        //判断请求条件的合法性
        if (nicknames == null){
            nicknames = new ArrayList<String>();
        }
        List<UserVo> res = new ArrayList<UserVo>();
        for (String nickname : nicknames){
            res.add(this.getUserByNickname(nickname));
        }

        if (CollectionUtils.isEmpty(res)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }

        return res;
    }
    //预警模块得到预警用户信息
    public List<UserVo> getAllUser(List listUserId) {
        return userVoMapper.getAllUser( listUserId );
    }


}

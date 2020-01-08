package com.treehole.member.controller;

import com.treehole.api.member.UserControllerApi;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("member/user")
public class UserController implements UserControllerApi {
    @Autowired
    private UserService userService;

    @Autowired UserVoService userVoService;
    @Override
    //当用户拥有权限时候方可访问此方法
    @PreAuthorize("hasAuthority('member_user_find_all')")
    @GetMapping("/getAllUserVos/{page}/{size}")
    public QueryResponseResult findAllUserVo(@PathVariable("page") Integer page,
                                             @PathVariable("size") Integer size,
                                             String sortBy,
                                             Boolean desc,
                                             UserListRequest userListRequest){
        return userVoService.findAllUserVos(page,size,sortBy,desc,userListRequest);
    }

    @Override
    @GetMapping("/getUserByNicknames")
    //@PreAuthorize("hasAuthority('member_user_find_names')")
    //@ApiImplicitParam(name = "name", value = "name集合",  allowMultiple = true, dataType = "String", paramType = "query")
    public List<UserVo> findUserByNicknames(@RequestParam(value ="names")List<String> names){
        return userVoService.getUserByNicknames(names);
    }

    @Override
    @GetMapping("/getUserByNickname")
    //@PreAuthorize("hasAuthority('member_user_find_nickname')")
    public UserVo getUserVoByNickname(@RequestParam(value = "nickname") String nickname){
        return userVoService.getUserByNickname(nickname);
    }

    @Override
    @GetMapping("/getUserByTime")
    //@PreAuthorize("hasAuthority('member_user_find_time')")
    public QueryResult findUserByTime(@RequestParam("beforeTime") String beforeTime,
                                      @RequestParam("afterTime") String afterTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date before = null;
        Date after = null;
        try {
            before = sdf.parse(beforeTime);
            after = sdf.parse(afterTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<UserVo> byTime = userVoService.findAllUserByTime(before, after);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(byTime);
        queryResult.setTotal(byTime.size());
        return queryResult;
    }
    //查询用户权限
    @Override
    @GetMapping("/getUserExt")
    //@PreAuthorize("hasAuthority('member_user_find_userext')")
    public UserExt getUserExt( @RequestParam("userNickName") String userNickName){
        return userService.getUserExt(userNickName);
    }

    @Override
    @DeleteMapping(value ="/delete/{user_id}")
    @PreAuthorize("hasAuthority('member_user_delete')")
    public ResponseResult deleteUserById(@PathVariable("user_id") String user_id) {
        userService.deleteUserById(user_id);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @PostMapping ("/insert")
    //@PreAuthorize("hasAuthority('member_user_insert')")
    public ResponseResult insertUser(@RequestBody @Valid User user) {
        userService.insertUser(user);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('member_user_update')")
    public ResponseResult update(@RequestBody  UserVo uservo){
        userService.updateUser(uservo);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    /*更新手机号绑定*/
    @Override
    @PutMapping("/update/phone")
    @PreAuthorize("hasAuthority('member_user_update_phone')")
    public ResponseResult updateUserPhone(@RequestParam(required=true,value = "user_id") String user_id,
                                          @RequestParam(required=true,value ="role_id") String role_id,
                                          @RequestParam(required=true,value ="user_phone") String user_phone){
        User user = new User();
        user.setUser_id(user_id);
        user.setRole_id(role_id);
        user.setUser_phone(user_phone);
        userService.updatePhone(user);
        return new ResponseResult(CommonCode.SUCCESS);

    }
    /*更新密码*/
    @Override
    @PutMapping("/update/password")
    @PreAuthorize("hasAuthority('member_user_update_password')")
    public ResponseResult updateUserPass(@RequestParam(value = "user_id") String id,
                                         @RequestParam(value ="OldPass") String OldPass,
                                         @RequestParam(value ="NewPass") String NewPass){
        userService.updatePass(id,OldPass,NewPass);
        return new ResponseResult(CommonCode.SUCCESS);

    }
    @Override
    @GetMapping("/get/warningUser")
    //@PreAuthorize("hasAuthority('member_user_find_listall')")
    public List<UserVo> getAllUser(@RequestParam("listUserId") List listUserId){
        return userVoService.getAllUser(listUserId);
    }

    @Override
    //@PreAuthorize("hasAuthority('member_user_find_userid')")
    @GetMapping("/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id){
        return userVoService.getUserByUserId(user_id);
    }

}

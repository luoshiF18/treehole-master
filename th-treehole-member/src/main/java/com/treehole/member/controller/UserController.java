package com.treehole.member.controller;

import com.treehole.api.member.UserControllerApi;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.domain.member.result.MemberCode;

import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.treehole.member.service.*;
import javax.validation.Valid;
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
    @GetMapping("/getAllUserVos/{page}/{size}")
    public QueryResponseResult findAllUserVo(@PathVariable("page") Integer page,
                                             @PathVariable("size") Integer size,
                                             UserListRequest userListRequest){

        return userVoService.findAllUserVos(page,size,userListRequest);
    }

    @Override
    @DeleteMapping(value ="/delete/id/{user_id}")
    public ResponseResult deleteUserById(@PathVariable("user_id") String user_id) {
         userService.deleteUserById(user_id);
         //再判断用户是否存在
         /*if(this.getUserById(user_id) != null){
             ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
         }*/
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @PostMapping ("/insert")
    public ResponseResult insertUser(@RequestBody @Valid User user) {

        if (userService.findUserByPhone(user.getUser_phone())!= null){  /*手机号唯一*/
            return new ResponseResult(MemberCode.PHONE_IS_EXIST);
        }
        userService.insertUser(user);

        return new ResponseResult(CommonCode.SUCCESS);


    }

    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody @Valid User user){
        //System.out.println("前端传来的+++++++++++++"+user);
        userService.updateUser(user);

        return new ResponseResult(CommonCode.SUCCESS);
    }
    /*更新手机号绑定*/
    @Override
    @PutMapping("/update/phone")
    public ResponseResult updateUserPhone(@RequestBody @Valid User user){
        if (userService.findUserByPhone(user.getUser_phone())!= null){  /*手机号唯一*/
            return new ResponseResult(MemberCode.PHONE_IS_EXIST);
        }
         userService.updateUser(user);
        return new ResponseResult(CommonCode.SUCCESS);


    }

    //public Result updateUserPassWord()



}

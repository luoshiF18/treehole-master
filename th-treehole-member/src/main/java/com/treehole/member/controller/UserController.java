package com.treehole.member.controller;

import com.treehole.api.member.UserControllerApi;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.domain.member.result.ResultEnum;
import com.treehole.framework.domain.member.result.ResultUtil;

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
@RequestMapping("user")
public class UserController implements UserControllerApi {
    @Autowired
    private UserService userService;

    @Autowired UserVoService userVoService;


    @GetMapping ("/getAllUsers")
    public Result getAllUser()  {
        List<User> user = userService.findAllUsers();
        if(user != null){
            //System.out.println("users:==============" +users);
            return ResultUtil.success(user);

        }else{
            return ResultUtil.error(ResultEnum.DATA_IS_NULL.getCode(),ResultEnum.DATA_IS_NULL.getMsg());
        }

    }

    @GetMapping("/find/id/{id}")
    public Result getUserById(@PathVariable("id") String id) {
        //System.out.println("==========+++++++++11111   "+id);
        List<User> res = userService.getUserById(id);
        //System.out.println("==========+++++++++      "+res);
        if(!res.isEmpty()){
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST.getCode(),ResultEnum.USER_NOT_EXIST.getMsg());
        }
    }

    @DeleteMapping(value ="/delete/id/{user_id}")
    public Result deleteUserById(@PathVariable("user_id") String user_id) {
        //1.判断用户是否存在
        //if(this.getUserById(user_id))
        int del = userService.deleteUserById(user_id);
        //System.out.println("}}}}}}}}}}}}"+ del);
        List<User> res=userService.getUserById(user_id);
        if(res.isEmpty()){
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.DELETE_FAIL.getCode(),ResultEnum.DELETE_FAIL.getMsg());
        }
    }

    @PostMapping ("/insert")
    public Result insertUser(@RequestBody @Valid User user) {

        if (userService.findUserByPhone(user.getUser_phone())!= null){  /*手机号唯一*/
            return ResultUtil.error(ResultEnum.USER_IS_EXISTS.getCode(),ResultEnum.USER_IS_EXISTS.getMsg());
        }
        int res=userService.insertUser(user);
        if(res==1){
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }
    /*接收到的数据为前端update后的*/
    @PostMapping("/update")
    public Result update(@RequestBody @Valid User user){
        //System.out.println("前端传来的+++++++++++++"+user);
        int res=userService.updateUser(user);
        if(res==0||res<0){
            return ResultUtil.error(ResultEnum.UPDATE_FAIL.getCode(),ResultEnum.UPDATE_FAIL.getMsg());

        }
        return ResultUtil.success();
    }
    /*更新手机号绑定*/

    @PostMapping("/update/phone")
    public Result updateUserPhone(@RequestBody @Valid User user){
        //System.out.println("前端传来的+++++++++++++"+user);
        if (userService.findUserByPhone(user.getUser_phone())!= null){  /*手机号唯一*/
            return ResultUtil.error(ResultEnum.PHONE_IS_EXISTS.getCode(),ResultEnum.PHONE_IS_EXISTS.getMsg());
        }
        int res = userService.updateUser(user);
        if(res>0){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultEnum.UPDATE_FAIL.getCode(),ResultEnum.UPDATE_FAIL.getMsg());
        }

    }

    //public Result updateUserPassWord()



}

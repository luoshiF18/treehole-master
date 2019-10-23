package com.treehole.member.controller;

import com.treehole.api.member.UserControllerApi;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.domain.member.result.ResultEnum;
import com.treehole.framework.domain.member.result.ResultUtil;

import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
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
    public User getUserById(@PathVariable("id") String id) {

         return userService.getUserById(id);


    }

    @DeleteMapping(value ="/delete/id/{user_id}")
    public ResponseResult deleteUserById(@PathVariable("user_id") String user_id) {


         userService.deleteUserById(user_id);
         //再判断用户是否存在
         if(this.getUserById(user_id) != null){
             ExceptionCast.cast(MemberCode.DELETE_FAIL);
         }
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @PostMapping ("/insert")
    public ResponseResult insertUser(@RequestBody @Valid User user) {

        if (userService.findUserByPhone(user.getUser_phone())!= null){  /*手机号唯一*/
            return new ResponseResult(MemberCode.PHONE_IS_EXISTS);
        }
        userService.insertUser(user);
        return new ResponseResult(CommonCode.SUCCESS);


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

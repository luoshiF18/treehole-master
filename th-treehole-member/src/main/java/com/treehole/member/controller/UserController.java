package com.treehole.member.controller;

import com.treehole.api.member.UserControllerApi;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.UserService;
import com.treehole.member.service.UserVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    //http://localhost:40300/user/getAllUsers?page=3
    @GetMapping ("/getAllUsers")
    public QueryResponseResult getAllUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size)  {
        QueryResult queryResult = userService.findAllUsers(page, size);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }

    @GetMapping("/find/id/{id}")
    public User getUserById(@PathVariable("id") String id) {

         return userService.getUserById(id);
    }
    @GetMapping("/find")
    public User getUser(@RequestBody @Valid User user){
        return  userService.findUser(user);

    }

    /*@GetMapping("/find/")
    public User getUser*/

    @DeleteMapping(value ="/delete/id/{user_id}")
    public ResponseResult deleteUserById(@PathVariable("user_id") String user_id) {
         userService.deleteUserById(user_id);
         //再判断用户是否存在
         /*if(this.getUserById(user_id) != null){
             ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
         }*/
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @PostMapping ("/insert")
    public ResponseResult insertUser(@RequestBody @Valid User user) {

        if (userService.findUserByPhone(user.getUser_phone())!= null){  /*手机号唯一*/
            return new ResponseResult(MemberCode.PHONE_IS_EXIST);
        }
        userService.insertUser(user);
        return new ResponseResult(CommonCode.SUCCESS);


    }
    /*接收到的数据为前端update后的*/
    @PostMapping("/update")
    public ResponseResult update(@RequestBody @Valid User user){
        //System.out.println("前端传来的+++++++++++++"+user);
        userService.updateUser(user);

        return new ResponseResult(CommonCode.SUCCESS);
    }
    /*更新手机号绑定*/

    @PostMapping("/update/phone")
    public ResponseResult updateUserPhone(@RequestBody @Valid User user){
        if (userService.findUserByPhone(user.getUser_phone())!= null){  /*手机号唯一*/
            return new ResponseResult(MemberCode.PHONE_IS_EXIST);
        }
         userService.updateUser(user);
        return new ResponseResult(CommonCode.SUCCESS);


    }
    @GetMapping("/findUser/nickname")
    public User findUserByNickname(@RequestParam("nickname") String nickname){

        return  userService.findUserByNickname( nickname );
    }
    @GetMapping("/getUserExt")
    public UserExt getUserExt( @RequestParam("userNickName") String userNickName){
        return userService.getUserExt(userNickName);
    }



}

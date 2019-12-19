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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    //@ApiImplicitParam(name = "name", value = "name集合",  allowMultiple = true, dataType = "String", paramType = "query")
    public List<UserVo> findUserByNicknames(@RequestParam(value ="names")List<String> names){
        return userVoService.getUserByNicknames(names);
    }

    @Override
    @GetMapping("/getUserByNickname")
    public UserVo getUserVoByNickname(@RequestParam(value = "nickname") String nickname){
        return userVoService.getUserByNickname(nickname);
    }

    @Override
    @GetMapping("/getUserByTime")
    public QueryResult findUserByTime(@RequestParam("beforeTime") Date beforeTime,
                                      @RequestParam("afterTime") Date afterTime){
        List<UserVo> byTime = userVoService.findAllUserByTime(beforeTime, afterTime);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(byTime);
        queryResult.setTotal(byTime.size());
        return queryResult;
    }
    @Override
    @GetMapping("/getUserExt")
    public UserExt getUserExt( @RequestParam("userNickName") String userNickName){
        return userService.getUserExt(userNickName);
    }

    @Override
    @DeleteMapping(value ="/delete/{user_id}")
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
        userService.insertUser(user);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody @Valid UserVo uservo){
        //System.out.println("前端传来的+++++++++++++"+user);
        userService.updateUser(uservo);

        return new ResponseResult(CommonCode.SUCCESS);
    }
    /*更新手机号绑定*/
    @Override
    @PutMapping("/update/phone")
    /*public ResponseResult updateUserPhone(@RequestBody User user){
        System.out.println("_____________" + user);
        userService.updatePhone(user);
        return new ResponseResult(CommonCode.SUCCESS);

    }*/
    public ResponseResult updateUserPhone(@RequestParam(required=true,value = "user_id") String user_id,
                                          @RequestParam(required=true,value ="role_id") String role_id,
                                          @RequestParam(required=true,value ="user_phone") String user_phone){
        User user = new User();
        user.setUser_id(user_id);
        user.setRole_id(role_id);
        user.setUser_phone(user_phone);
        System.out.println("_____________" + user);
        userService.updatePhone(user);
        return new ResponseResult(CommonCode.SUCCESS);

    }
    /*更新密码*/
    @Override
    @PutMapping("/update/password")
    public ResponseResult updateUserPass(@RequestParam(value = "user_id") String id,
                                         @RequestParam(value ="OldPass") String OldPass,
                                         @RequestParam(value ="NewPass") String NewPass){
        userService.updatePass(id,OldPass,NewPass);
        return new ResponseResult(CommonCode.SUCCESS);

    }
    @Override
    @GetMapping("/get/warningUser")
    public List<UserVo> getAllUser(@RequestParam("listUserId") List listUserId){
        return userVoService.getAllUser(listUserId);
    }

    @Override
    @GetMapping("/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id){
        return userVoService.getUserByUserId(user_id);
    }


}

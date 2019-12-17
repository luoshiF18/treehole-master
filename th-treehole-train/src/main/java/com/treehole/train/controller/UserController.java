package com.treehole.train.controller;
import com.treehole.api.train.UserControllerApi;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/train/login")
public class UserController implements UserControllerApi {

    @Autowired
    UserService userService;

    @Override
    @GetMapping("/login/{userName}/{userPassword}")
    public ResponseResult login(@PathVariable("userName") String userName,@PathVariable("userPassword") String userPassword, HttpServletRequest request) {
        return userService.login(userName,userPassword,request);
    }

    @Override
    @GetMapping("/signOut")
    public ResponseResult signOut(HttpServletRequest request) {
        return userService.SignOut(request);
    }

    @Override
    @GetMapping("/updatePassword/{userName}/{oldPwd}/{newPwd}")
    public ResponseResult updatePassword(@PathVariable("userName") String userName,@PathVariable("oldPwd") String oldPwd,@PathVariable("newPwd") String newPwd) {
        return userService.updatePassword(userName, oldPwd, newPwd);
    }


}

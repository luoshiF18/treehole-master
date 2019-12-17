package com.document.manage.controller;

import com.document.manage.pojo.User;
import com.document.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: lee
 * @Date: 2019/12/16
 * @Description: com.lee.springboot.controller
 * @version: 1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    //登录
    @RequestMapping("login")
    public String hello(String username,String password,Model model){
        System.out.println("username:"+username+",password:"+password);
        User userLogin = userServiceImpl.userLogin(username, password);
        if(userLogin!=null){
            model.addAttribute("loginUser",userLogin);
            return "main";
        }else{
            model.addAttribute("errorMsg","错误");
            return "index";
        }
    }

    //修改密码
    @RequestMapping("updatedPwd")
    public String updatedPwd(String userId,String oldPwd,String newPwd){
        System.out.println(userId+oldPwd+newPwd);
        return "index";
    }

    //添加用户
    @RequestMapping("addUser")
    public String addUser(){
        System.out.println("添加用户");
        return null;
    }



}

package com.document.manage.controller;

import com.document.manage.pojo.EasyUIResult;
import com.document.manage.pojo.User;
import com.document.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("user.html")
    public String userShow(){
        return "user";
    }
    //展示所有用户
    @RequestMapping("/user/show")
    @ResponseBody
    public EasyUIResult userTable(@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int rows){
        return userServiceImpl.userShow(page,rows);
    }

    //登录
    @RequestMapping("login")
    public String hello(String username,String password,Model model){
        User userLogin = userServiceImpl.userLogin(username, password);
        if(userLogin!=null){
            model.addAttribute("loginUser",userLogin);
            return "main";
        }else{
            model.addAttribute("msg","用户名或密码错误");
            return "index";
        }
    }

    //修改密码
    @RequestMapping("updatedUserPwd")
    public String updatedPwd(String userId,String oldPwd,String newPwd,Model model){
        int index = userServiceImpl.updatedPwd(userId, oldPwd, newPwd);
        if(index==1){
            model.addAttribute("msg","修改成功，请重新登录！");
            return "index";
        }else {
            model.addAttribute("msg","修改失败！");
            return "index";
        }
    }

    @RequestMapping("updatedUserName")
    @ResponseBody
    public String updatedName(String userId,String oldName,String newName){
        int index = userServiceImpl.updatedName(userId, oldName, newName);
        if(index==1){
            return "修改用户名成功！";
        }
        return "修改用户名失败！";
    }

    //添加用户
    @RequestMapping("addUser")
    @ResponseBody
    public String addUser(String name){
        int index = userServiceImpl.addUser(name);
        if(index==1){
            return "创建成功，初始密码为000";
        }else {
            return "创建失败！";
        }
    }

    //删除用户
    @RequestMapping("deleteUser")
    @ResponseBody
    public String deleteUser(String userId){
        int index = userServiceImpl.deleteUser(userId);
        if(index==1){
            return "删除成功！";
        }else {
            return "删除失败！";
        }
    }

}

package com.treehole.train.service;

import com.treehole.framework.domain.train.User;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //登录
    public ResponseResult login(String userName, String userPassword, int userType , HttpServletRequest request){

        User user = userRepository.findByUserName(userName);
        System.out.println(user);
        if(user != null){
            System.out.println(user);
            if(user.getUserPassword().equals(userPassword)){
                if(user.getUserType() == userType ){
                    //登录成功
                    request.getSession().setAttribute("login",userName);
                    return new ResponseResult(CommonCode.SUCCESS);
                }else {
                    //登录失败
                    return new ResponseResult(CommonCode.FAIL);
                }
            }else {
                //登录失败
                return new ResponseResult(CommonCode.FAIL);
            }
        }else {
            //登录失败
            return new ResponseResult(CommonCode.FAIL);
        }

    }


    //修改密码
    public ResponseResult updatePassword(String userName,String oldPwd,String newPwd){
        System.out.println(userName);
        User user = userRepository.findByUserName(userName);
        if(user.getUserPassword() .equals(oldPwd) ){
            //修改密码
            user.setUserPassword(newPwd);
            userRepository.save(user);
            return new ResponseResult(CommonCode.SUCCESS);
        }else {
            return new ResponseResult(TrainCode.THE_ORIGINAL_PASSWORD_IS_WRONG);
        }
    }


}

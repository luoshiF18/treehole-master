package com.document.manage.service.impl;

import com.document.manage.mapper.UserMapper;
import com.document.manage.pojo.User;
import com.document.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lee
 * @Date: 2019/12/16
 * @Description: com.lee.springboot.service.impl
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(String username, String password) {
        return userMapper.findUserByUserNameAndPassword(username, password);
    }
}

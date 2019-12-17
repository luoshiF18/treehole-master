package com.document.manage.service;

import com.document.manage.pojo.User;

/**
 * @Auther: lee
 * @Date: 2019/12/16
 * @Description: com.lee.springboot.service
 * @version: 1.0
 */
public interface UserService {
    User userLogin(String username, String password);
}

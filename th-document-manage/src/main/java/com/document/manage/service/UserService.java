package com.document.manage.service;

import com.document.manage.pojo.EasyUIResult;
import com.document.manage.pojo.User;

/**
 * @Auther: lee
 * @Date: 2019/12/16
 * @Description: com.lee.springboot.service
 * @version: 1.0
 */
public interface UserService {
    /**
     * 登录功能
     * @param username
     * @param password
     * @return
     */
    User userLogin(String username, String password);

    /**
     * 用户表格展示
     * @return
     */
    EasyUIResult userShow(int page, int rows);

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    int updatedPwd(String userId, String oldPwd, String newPwd);

    /**
     * 新增用户
     * @param userName
     * @return
     */
    int addUser(String userName);

    /**
     * 修改名字
     * @param userId
     * @param oldName
     * @param newName
     * @return
     */
    int updatedName(String userId, String oldName, String newName);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int deleteUser(String userId);
}

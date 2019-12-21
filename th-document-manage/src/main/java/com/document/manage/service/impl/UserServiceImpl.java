package com.document.manage.service.impl;

import com.document.manage.mapper.UserMapper;
import com.document.manage.pojo.EasyUIResult;
import com.document.manage.pojo.User;
import com.document.manage.service.UserService;
import com.document.manage.utils.IDUtils;
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

    @Override
    public EasyUIResult userShow(int page, int rows) {
        EasyUIResult result = new EasyUIResult();
        result.setRows(userMapper.findAllByPage(rows * (page - 1) == 0 ? 0 : rows * (page - 1) - 1, rows));
        result.setTotal(userMapper.findAll().size());
        return result;
    }

    @Override
    public int updatedPwd(String userId, String oldPwd,String newPwd) {
        if (oldPwd!=newPwd||!oldPwd.equals(newPwd)){
            User user = userMapper.findUserByIdAndPassword(userId, oldPwd);
            if(user!=null){
                user.setPassword(newPwd);
                User save = userMapper.save(user);
                if(save!=null){
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public int addUser(String userName) {
        if(userName!=null&&!userName.equals("")){
            User addUser = new User();
            addUser.setId(IDUtils.genUserId());
            addUser.setUserName(userName);
            addUser.setPassword("000");
            addUser.setIsAdmin(0);
            User save = userMapper.save(addUser);
            if(save!=null){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int updatedName(String userId, String oldName,String newName) {
        if(userId!=null&&!userId.equals("")){
            User user = userMapper.findUserByIdAndUserName(userId, oldName);
            if(user!=null){
                user.setUserName(newName);
                if(userMapper.save(user)!=null){
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public int deleteUser(String userId) {
        User user = userMapper.findUserById(userId);
        if(user!=null){
            userMapper.delete(user);
            return 1;
        }
        return 0;
    }
}

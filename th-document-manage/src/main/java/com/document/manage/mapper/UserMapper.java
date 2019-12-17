package com.document.manage.mapper;

import com.document.manage.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: lee
 * @Date: 2019/12/16
 * @Description: com.lee.springboot.mapper
 * @version: 1.0
 */
public interface UserMapper extends JpaRepository<User,String> {
    User findUserByUserNameAndPassword(String userName, String password);
}

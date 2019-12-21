package com.document.manage.mapper;

import com.document.manage.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/12/16
 * @Description: com.lee.springboot.mapper
 * @version: 1.0
 */
public interface UserMapper extends JpaRepository<User,String> {
    User findUserByUserNameAndPassword(String userName, String password);
    User findUserByIdAndPassword(String id, String password);
    User findUserByIdAndUserName(String id, String userName);
    User findUserById(String id);
    @Query(value = "select * from user limit ?,? " , nativeQuery = true)
    List<User> findAllByPage(int pageIndex, int rows);
}

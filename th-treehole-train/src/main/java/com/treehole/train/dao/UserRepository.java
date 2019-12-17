package com.treehole.train.dao;

import com.treehole.framework.domain.train.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    public User findByUserName(String userName);

}

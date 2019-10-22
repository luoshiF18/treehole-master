package com.treehole.member.service;

import com.treehole.member.mapper.RoleMapper;
import com.treehole.member.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
}

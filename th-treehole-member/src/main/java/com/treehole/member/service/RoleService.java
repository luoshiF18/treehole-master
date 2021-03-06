package com.treehole.member.service;

import com.treehole.framework.domain.member.Role;
import com.treehole.member.mapper.RoleMapper;
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

    public Role findRoleByRole(Role role){
        return roleMapper.selectOne(role);
    }

}

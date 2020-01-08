package com.treehole.api.member;

import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shanhuijie
 * @Description: 角色api
 * @Date 2019.10.21 21:05
 */

@Api(value = "用户角色信息管理", description = "对用户角色信息进行增、删、改、查")
public interface RoleControllerApi {
    @ApiOperation("查询所有角色信息")
    public QueryResponseResult findAllRole(Integer page,
                                           Integer size,
                                           RoleListRequest roleListRequest);
    @ApiOperation("无条件查询所有角色信息")
    public QueryResponseResult findAll();

    @ApiOperation("根据名称查询角色信息")
    public Role findByName(String name);
    @ApiOperation("插入一条角色信息")
    public ResponseResult insertRole(Role role) ;

    @ApiOperation("根据id删除角色信息")
    public ResponseResult deleteRole(String role_id);

    @ApiOperation("更改角色信息")
    public ResponseResult updateRole(Role role);

}

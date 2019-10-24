package com.treehole.api.member;

import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author shanhuijie
 * @Description: 角色api
 * @Date 2019.10.21 21:05
 */

@Api(value = "用户角色信息管理", description = "对用户角色信息进行增、删、改、查")
public interface RoleControllerApi {
    @ApiOperation("查询所有角色信息")
    public Result findAllRoles();

    @ApiOperation("插入一条角色信息")
    public Result insertRole(@RequestBody @Valid Role role) ;

    @ApiOperation("根据id删除角色信息")
    public Result deleteRoleById(@PathVariable("role_id")String role_id);

    @ApiOperation("更改角色信息")
    public Result update(@RequestBody @Valid Role role);

}

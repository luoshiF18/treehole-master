package com.treehole.api.member;

import com.treehole.framework.domain.member.Permission;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.Vo.PermissionVo;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author shanhuijie
 * @Description: 权限api
 * @Date 2019.10.21 21:05
 */

@Api(value = "角色权限信息管理", description = "对角色权限信息进行增、删、改、查")
public interface PermissionControllerApi {
  /*  @ApiOperation("查询所有permission信息")
    public QueryResponseResult findAllPermission(Integer page,
                                                 Integer size
                                                );*/

   /* @ApiOperation("根据id查询perssion信息")
    public Permission findPermissionById(String id);*/

    @ApiOperation("根据role_id查询perssion信息")
    public List<PermissionVo> findPermissionByRoleId(String role_id);

    @ApiOperation("插入一条permission信息")
    public ResponseResult insertPermission(PermissionVo permissionvo);

    @ApiOperation("根据id删除角色权限信息")
    public ResponseResult deletePermissionById(String id);

   /* @ApiOperation("更改权限信息")
    public ResponseResult updateThMenu(ThMenu thMenu);*/

}

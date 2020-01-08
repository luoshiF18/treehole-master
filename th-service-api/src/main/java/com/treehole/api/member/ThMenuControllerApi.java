package com.treehole.api.member;

import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shanhuijie
 * @Description: 权限api
 * @Date 2019.10.21 21:05
 */

@Api(value = "权限信息管理", description = "对权限信息进行增、删、改、查")
public interface ThMenuControllerApi {
    @ApiOperation("查询所有权限信息")
    public QueryResponseResult findAllThMenu(Integer page,
                                             Integer size,
                                             String menu_name,
                                             String pid
    );

    @ApiOperation("根据id查询权限信息")
    public ThMenu findThMenuById(Integer thMenu_id);

    @ApiOperation("无条件查询所有父菜单信息")
    public QueryResponseResult findAllf();

    @ApiOperation("无条件查询所有角色信息")
    public QueryResponseResult findAllByPid(Integer pid);

    @ApiOperation("插入一条权限信息")
    public ResponseResult insertThMenu(ThMenu thMenu) ;

    @ApiOperation("根据id删除权限信息")
    public ResponseResult deleteThMenuById(Integer thMenu_id);

    @ApiOperation("更改权限信息")
    public ResponseResult updateThMenu(ThMenu thMenu);

}

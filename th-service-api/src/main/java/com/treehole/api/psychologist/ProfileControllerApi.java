package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/10/29 16:58
 */
@Api(value = "心理咨询师简介信息管理", description = "对心理咨询师简介信息进行增、删、改、查")
public interface ProfileControllerApi {

    @ApiOperation("根据条件分页查询心理咨询师简介信息")
    QueryResponseResult findAllProfile(Integer page, Integer size, String name, String sex, String qualification);

    @ApiOperation("根据id查询心理咨询师简介信息")
    Profile findProfileById(String id);

    @ApiOperation("根据id删除心理咨询师简介信息")
    ResponseResult delProfileById(String id);

    @ApiOperation("添加心理咨询师简介信息")
    ResponseResult addProfile(Profile profile);

    @ApiOperation("根据id更新心理咨询师简介信息")
    ResponseResult updateProfile(Profile profile);
}

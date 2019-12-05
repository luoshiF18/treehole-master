package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/11/16 9:08
 */
@Api(value = "心理咨询师详情信息管理", description = "对心理咨询师详情信息进行增、删、改、查")
public interface DetailControllerApi {

    @ApiOperation("根据咨询师id查询咨询师详情信息")
    Detail getDetailById(String psychologist_id);

    @ApiOperation("根据咨询师id删除咨询师详情信息")
    ResponseResult delDetailById(String psychologist_id);

    @ApiOperation("根据id更新咨询师详情信息")
    ResponseResult updateDetail(Detail detail);

    @ApiOperation("根据咨询师姓名查询咨询师详情信息")
    QueryResponseResult getDetailByName(Integer page, Integer size, String psychologist_name);

}

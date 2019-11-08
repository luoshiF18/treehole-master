package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.State;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/10/29 18:54
 */
@Api(value = "心理咨询师状态信息管理", description = "对心理咨询师状态信息进行增、删、改、查")
public interface StateControllerApi {

    @ApiOperation("根据条件分页查询心理咨询师状态信息列表")
    QueryResponseResult findStateList(Integer page, Integer size, String name);

    @ApiOperation("根据id查询心理咨询师状态信息")
    State findStateById(String id);

    @ApiOperation("根据id删除心理咨询师状态信息")
    ResponseResult delStateById(String id);

    @ApiOperation("添加心理咨询师状态信息")
    ResponseResult addState(State state);

    @ApiOperation("根据id更新心理咨询师状态信息")
    ResponseResult updateState(State state);

}

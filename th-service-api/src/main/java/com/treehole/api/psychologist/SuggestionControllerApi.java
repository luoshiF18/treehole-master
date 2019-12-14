package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.Suggestion;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/11/19 14:42
 */
@Api(value = "咨询师建议相关接口", description = "对咨询师给出的建议进行增、删、改、查")
public interface SuggestionControllerApi {

    @ApiOperation("根据建议id查询建议信息")
    QueryResponseResult getSuggestionById(Integer page, Integer size, String suggestion_id);

    @ApiOperation("分页查询所有建议信息")
    QueryResponseResult getAllSuggestions(Integer page, Integer size);

    @ApiOperation("根据id删除建议信息")
    ResponseResult delSuggestionById(String suggestion_id);

    @ApiOperation("添加一条建议信息")
    ResponseResult addSuggestion(Suggestion suggestion);

    @ApiOperation("更新建议信息")
    ResponseResult updateSuggestion(Suggestion suggestion);

    @ApiOperation("根据主键查询")
    Suggestion findSuggestionById(String suggestion_id);
}

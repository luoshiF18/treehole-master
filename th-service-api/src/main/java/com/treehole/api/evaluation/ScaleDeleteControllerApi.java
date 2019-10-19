package com.treehole.api.evaluation;

import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@Api(value = "量表删除管理", description = "对量表进行删除")
public interface ScaleDeleteControllerApi {

    @ApiOperation("删除量表")
    public ResponseResult deleteScale(String scaleId);

    @ApiOperation("删除某个用户的测评结果")
    public ResponseResult deleteUserScale(String userId);

    @ApiOperation("删除某个问题")
    public ResponseResult deleteQuestion(String questionId);

    @ApiOperation("删除某个选项")
    public ResponseResult deleteOption(String optionId);
}

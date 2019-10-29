package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Api(value = "量表增加管理", description = "对量表进行增加")
public interface ScaleInsertControllerApi {

    @ApiOperation("添加新量表")
    public ResponseResult insertScale(Scale scale);

    @ApiOperation("添加问题和选项")
    public ResponseResult insertQuestionAndOption(QuestionDTO questionDTO);

    @ApiOperation("添加得分描述")
    public ResponseResult insertDescription(Description description);

}

package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Result;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@Api(value = "量表更改管理", description = "对量表进行更改")
public interface ScaleUpdateControllerApi {

    @ApiOperation("更改量表信息")
    public ResponseResult updateScaleInfo(Scale scale);

    @ApiOperation("更改量表问题和选项信息")
    public ResponseResult updateQuestion(QuestionDTO questionDTO);

    @ApiOperation("更改量表描述信息")
    public ResponseResult updateDesc(Description description);

    @ApiOperation("更改结果信息")
    public ResponseResult updateResult(Result result);

}

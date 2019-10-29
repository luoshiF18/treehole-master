package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 8:56 on 2019/10/17.
 * Version: 1.0
 */
@Api(value = "预警管理", description = "对预警信息的添加，修改，查询")
public interface WarningControllerApi {

    @ApiOperation("根据描述表预警等级得出预警信息")
    public ResponseResult getWarning(String descriptionId);
    @ApiOperation("根据用户id查询该用户的预警信息")
    public List<Warning> findWarningByUserId(String userId);

    @ApiOperation("心理咨询师添加预警信息")
    public ResponseResult addWarningByPsy(String userId);
}

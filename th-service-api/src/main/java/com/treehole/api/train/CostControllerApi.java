package com.treehole.api.train;

import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="交费管理页面管理接口",description = "交费管理页面管理接口，提供页面的交费")
public interface CostControllerApi {

    @ApiOperation("交费")
    public ResponseResult pay(String studentId,Cost cost);




}

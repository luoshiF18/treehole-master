package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.response.StartTestResult;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@Api(value = "量表查询管理", description = "对量表进行查询")
public interface ScaleSelectControllerApi {

    @ApiOperation("搜索量表")
    public QueryResponseResult findScale(Integer page, Integer size, String sortBy, Boolean desc, String key);

    @ApiOperation("开始测试")
    public StartTestResult startTest(String scaleId);
}

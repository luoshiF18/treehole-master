package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Api(value = "量表管理", description = "对量表进行管理（增删改查等）")
public interface ScaleManageControllerApi {

    @ApiOperation("添加新量表")
    public ResponseResult insertScale(Scale scale);

    @ApiOperation("搜索量表")
    public QueryResponseResult findScale(Integer page, Integer size, String sortBy, Boolean desc, String key);
}

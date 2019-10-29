package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.Type;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wanglu
 */
@Api(value = "类型管理", description = "对优惠券/活动类型进行管理")
public interface TypeControllerApi {
    @ApiOperation("搜索优惠券类型/活动类型")
    public QueryResponseResult queryTypes(Boolean usedFor);

    @ApiOperation("添加类型")
    public ResponseResult saveType(Type type);
}

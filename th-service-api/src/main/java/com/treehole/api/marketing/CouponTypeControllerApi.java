package com.treehole.api.marketing;

import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "优惠券类型管理", description = "对优惠券类型进行管理")
public interface CouponTypeControllerApi {
    @ApiOperation("搜索优惠券列表")
    public QueryResponseResult queryCouponType();
}

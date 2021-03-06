package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.bo.CouponBo;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户的优惠券管理", description = "对用户的优惠券进行管理")
public interface UserCouponControllerApi {

    @ApiOperation("查询用户的优惠券信息列表")
    public QueryResponseResult queryUserCoupons(String userId, Boolean status, Boolean usedStatus);

    @ApiOperation("查询用户可用优惠券总数")
    public int queryUserCouponCount(String userId);
    @ApiOperation("添加用户优惠券信息")
    public ResponseResult saveUserCoupon(CouponBo couponBo, String userId);
}

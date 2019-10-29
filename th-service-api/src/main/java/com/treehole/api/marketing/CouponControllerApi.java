package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.dto.CouponDTO;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wanglu
 */
@Api(value = "优惠券管理", description = "对优惠券进行管理")
public interface CouponControllerApi {


    @ApiOperation("搜索优惠券列表")
    public QueryResponseResult queryCouponByPage(String key, Integer page, Integer size, String sortBy, Boolean desc);

    @ApiOperation("根据优惠券id查询详细信息")
    public Coupon queryCouponById(String cid);


    @ApiOperation("添加优惠券")
    public ResponseResult saveCoupon(CouponDTO couponDTO);

    @ApiOperation("更改优惠券信息")
    public ResponseResult updateCouponInfo(CouponDTO couponDTO);

    @ApiOperation("根据id删除优惠券")
    public ResponseResult deleteCouponById(String cid);

}

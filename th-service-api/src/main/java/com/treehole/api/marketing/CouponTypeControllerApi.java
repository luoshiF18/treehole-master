package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.CouponType;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;


@Api(value = "优惠券类型管理", description = "对优惠券类型进行管理")
public interface CouponTypeControllerApi {
    @ApiOperation("搜索优惠券类型")
    public QueryResponseResult queryCouponType();

    @ApiOperation("更新优惠券类型信息")
    public ResponseResult updateCouponType(CouponType couponType);

    @ApiOperation("添加优惠券类型信息")
    public ResponseResult addCouponType(CouponType couponType);

    @ApiOperation("删除优惠券类型信息")
    public ResponseResult delCouponType(Integer id);

    @ApiOperation("根据id查询优惠券信息")
    public CouponType queryCouponTypeById(Integer id);

}

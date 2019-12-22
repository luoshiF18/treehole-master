package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.bo.CouponBo;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wanglu
 */
@Api(value = "优惠券管理", description = "对优惠券进行管理")
public interface CouponControllerApi {


    @ApiOperation("搜索优惠券列表")
    public QueryResponseResult queryCouponByPage(@RequestParam(value = "key", required = false)String key,
                                                 @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                 @RequestParam(value = "size", defaultValue = "5")Integer size,
                                                 @RequestParam(value = "sortBy", required = false)String sortBy,
                                                 @RequestParam(value = "desc", defaultValue = "true")Boolean desc,
                                                 @RequestParam(value = "status", required = false)Integer status);

    @ApiOperation("根据优惠券id查询详细信息")
    public Coupon queryCouponById(@PathVariable("id") String id);

    @ApiOperation("根据优惠券id查询用户可看到的优惠券信息")
    public CouponBo queryCouponBoById(@PathVariable("id") String id);

    @ApiOperation("添加优惠券")
    public ResponseResult saveCoupon(@RequestBody Coupon coupon);

    @ApiOperation("更改优惠券信息")
    public ResponseResult updateCouponInfo(@RequestBody Coupon coupon);

    @ApiOperation("修改优惠券状态为结束")
    public  ResponseResult updateStatusToFinished(@PathVariable("id") String id);
    @ApiOperation("根据id删除优惠券")
    public ResponseResult deleteCouponById(@PathVariable("id") String id);

    @ApiOperation("根据发布时间查优惠券")
    public QueryResponseResult queryValidCouponByReleaseTime(@RequestParam("today") String today);

}

package com.treehole.marketing.controller;

import com.treehole.api.marketing.UserCouponControllerApi;
import com.treehole.framework.domain.marketing.bo.CouponBo;
import com.treehole.framework.domain.marketing.request.UserCouponRequest;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/coupon")
public class UserCouponController implements UserCouponControllerApi {

    @Autowired
    private UserCouponService userCouponService;

    /**
     * 查询用户优惠券列表
     * @param userId
     * @param status
     * @param usedStatus
     * @return
     */
    @GetMapping("/my_coupon_list")
    public QueryResponseResult queryUserCoupons(
            @RequestParam("userId") String userId,
            @RequestParam(value = "status", defaultValue = "true") Boolean status,
            @RequestParam(value = "usedStatus",required = false) Boolean usedStatus) {
        QueryResult queryResult = this.userCouponService.queryUserCoupons(userId, status, usedStatus);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 查询用户未失效优惠券数目
     * @param userId
     * @return
     */
    @GetMapping("/count/{userId}")
    public int queryUserCouponCount(@PathVariable String userId) {
        return this.userCouponService.queryUserCouponCount(userId);
    }

    /**
     * 用户领取优惠券
     * @param userCouponRequest
     * @return
     */
    @PostMapping("/my_coupon")
    public ResponseResult saveUserCoupon(@RequestBody UserCouponRequest userCouponRequest) {
        this.userCouponService.saveUserCoupon(userCouponRequest);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping("/user/used/{id}")
    public ResponseResult changeUsedStatusToTrue(@PathVariable("id") String id) {
        this.userCouponService.changeUsedStatusToTrue(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @GetMapping("/user/used/statistics/{id}")
    public String queryCouponUsedData(@PathVariable("id") String id) {
        return this.userCouponService.queryCouponUsedData(id);
    }
}

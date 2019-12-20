package com.treehole.marketing.controller;

import com.treehole.api.marketing.CouponControllerApi;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.bo.CouponBo;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/coupon")
public class CouponController implements CouponControllerApi {

    @Autowired
    private CouponService couponService;
    /**
     * 根据查询条件(优惠券名称)分页并排序查询coupon信息
     * @param key
     * @param page
     * @param page
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("/page")
    public QueryResponseResult queryCouponByPage(@RequestParam(value = "key", required = false)String key,
                                                 @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                 @RequestParam(value = "size", defaultValue = "5")Integer size,
                                                 @RequestParam(value = "sortBy", required = false)String sortBy,
                                                 @RequestParam(value = "desc", defaultValue = "true")Boolean desc,
                                                 @RequestParam(value = "status", required = false)Integer status) {
        QueryResult queryResult = this.couponService.queryCouponByPage(key, page, size, sortBy, desc, status);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据优惠券id查询优惠券信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Coupon queryCouponById(@PathVariable("id") String id) {
        return this.couponService.queryCouponById(id);
    }

    /**
     * 根据优惠券id查询用户可以看到的
     * @param id
     * @return   门户网站用户看到的优惠券信息
     */
    @GetMapping("/cdetail/{id}")
    public CouponBo queryCouponBoById(@PathVariable("id") String id) {
        return this.couponService.queryCouponBoById(id);
    }


    /**
     * 添加优惠券
     * @param coupon
     * @return
     */
    @PostMapping
    public ResponseResult saveCoupon(@RequestBody Coupon coupon) {
        this.couponService.saveCoupon(coupon);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更新优惠券信息
     * @param coupon
     * @return
     */
    @PutMapping
    public ResponseResult updateCouponInfo(@RequestBody Coupon coupon) {
        this.couponService.updateCouponInfo(coupon);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 修改优惠券状态为结束
     * @return
     */
    @PutMapping("/status/{id}")
    public ResponseResult updateStatusToFinished(@PathVariable("id") String id) {
        this.couponService.updateStatusToFinished(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除优惠券
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteCouponById(@PathVariable("id") String id) {
        this.couponService.deleteCouponById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}

package com.treehole.marketing.controller;

import com.treehole.api.marketing.CouponTypeControllerApi;
import com.treehole.framework.domain.marketing.CouponType;
import com.treehole.framework.domain.marketing.InteractiveActivity;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.CouponTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/coupon_type")
public class CouponTypeController implements CouponTypeControllerApi {

    @Autowired
    private CouponTypeService couponTypeService;

    @GetMapping("/all")
    public QueryResponseResult queryCouponType() {
        QueryResult<CouponType> queryResult = this.couponTypeService.queryCouponType();
        return new QueryResponseResult( CommonCode.SUCCESS, queryResult);
    }

    @GetMapping("/{id}")
    public CouponType queryCouponTypeById(@PathVariable("id") Integer id){
        return this.couponTypeService.queryCouponTypeById(id);
    }
    @Override
    @PutMapping
    public ResponseResult updateCouponType(@RequestBody CouponType couponType) {
        this.couponTypeService.updateCouponType(couponType);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PostMapping
    public ResponseResult addCouponType(@RequestBody CouponType couponType) {
        this.couponTypeService.addCouponType(couponType);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据ID删除优惠券类型
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseResult delCouponType(@PathVariable("id") Integer id) {
        this.couponTypeService.delCouponType(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}

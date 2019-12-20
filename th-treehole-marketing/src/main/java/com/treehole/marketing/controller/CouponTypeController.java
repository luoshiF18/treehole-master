package com.treehole.marketing.controller;

import com.treehole.api.marketing.CouponTypeControllerApi;
import com.treehole.framework.domain.marketing.CouponType;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.service.CouponTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

package com.treehole.marketing.controller;

import com.treehole.api.marketing.CouponControllerApi;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.dto.CouponDTO;
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
@RequestMapping("/coupon")
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
                                                 @RequestParam(value = "desc", required = false)Boolean desc) {
        QueryResult queryResult = this.couponService.queryCouponByPage(key, page, size, sortBy, desc);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据优惠券id查询优惠券信息
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public Coupon queryCouponById(String cid) {
        return this.couponService.queryCouponById(cid);
    }



    /**
     * 添加优惠券
     * @param couponDTO
     * @return
     */
    @PostMapping
    public ResponseResult saveCoupon(CouponDTO couponDTO) {
        this.couponService.saveCoupon(couponDTO);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更新优惠券信息
     * @param couponDTO
     * @return
     */
    @PutMapping
    public ResponseResult updateCouponInfo(CouponDTO couponDTO) {
        this.couponService.updateCouponInfo(couponDTO);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除优惠券
     * @param cid
     * @return
     */
    @DeleteMapping
    public ResponseResult deleteCouponById(String cid) {
        this.couponService.deleteCouponById(cid);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}

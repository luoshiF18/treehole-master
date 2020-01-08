package com.treehole.marketing.dao;

import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.response.StatisticsData;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wanglu
 */
public interface CouponMapper extends Mapper<Coupon> {

    List<Coupon> queryValidCoupon(String today);

    List<StatisticsData> querySomeCouponStock(String id);
}

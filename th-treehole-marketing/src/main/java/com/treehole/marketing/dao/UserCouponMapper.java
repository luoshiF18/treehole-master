package com.treehole.marketing.dao;

import com.treehole.framework.domain.marketing.UserCoupon;
import com.treehole.framework.domain.marketing.response.StatisticsData;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wanglu
 */
public interface UserCouponMapper extends Mapper<UserCoupon> {
    List<StatisticsData> queryCouponUsedData(String id);
}

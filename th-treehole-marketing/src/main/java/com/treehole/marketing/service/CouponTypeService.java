package com.treehole.marketing.service;

import com.treehole.framework.domain.marketing.CouponType;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.CouponTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author wanglu
 */
@Service
public class CouponTypeService {

    @Autowired
    private CouponTypeMapper couponTypeMapper;


    public QueryResult<CouponType> queryCouponType() {
        List<CouponType> couponTypes = this.couponTypeMapper.selectAll();
        if(CollectionUtils.isEmpty(couponTypes)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return new QueryResult<>(couponTypes, couponTypes.size());
    }
}

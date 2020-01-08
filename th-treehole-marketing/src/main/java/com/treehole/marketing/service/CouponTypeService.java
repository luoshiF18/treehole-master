package com.treehole.marketing.service;

import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.CouponType;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.CouponMapper;
import com.treehole.marketing.dao.CouponTypeMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author wanglu
 */
@Service
public class CouponTypeService {

    @Autowired
    private CouponTypeMapper couponTypeMapper;
    @Autowired
    private CouponMapper couponMapper;


    public QueryResult<CouponType> queryCouponType() {
        List<CouponType> couponTypes = this.couponTypeMapper.selectAll();
        if(CollectionUtils.isEmpty(couponTypes)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return new QueryResult<>(couponTypes, couponTypes.size());
    }

    public void updateCouponType(CouponType couponType) {
        if(StringUtils.isEmpty(couponType.getUsedType())||
        StringUtils.isEmpty(couponType.getRule())){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }

        if(this.couponTypeMapper.updateByPrimaryKeySelective(couponType) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }
    }

    public void addCouponType(CouponType couponType) {
        if(StringUtils.isEmpty(couponType.getUsedType())||
                StringUtils.isEmpty(couponType.getRule())){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        couponType.setId(MyNumberUtils.getUUID());
        if(this.couponTypeMapper.insertSelective(couponType) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }

    }

    public void delCouponType(String id) {
        if(id == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //根据优惠券类型查找优惠券，若存在该类型优惠券则不允许删除优惠类型
        Coupon coupon = new Coupon();
        coupon.setUsedTypeId(id);
        List<Coupon> select = this.couponMapper.select(coupon);
        if(!CollectionUtils.isEmpty(select)){
            ExceptionCast.cast(MarketingCode.COUPONTYPE_DEL_FORBIDDEN);
        }
        if(this.couponTypeMapper.deleteByPrimaryKey(id) != 1){
            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
        }
    }

    public CouponType queryCouponTypeById(String id) {
        if(id == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }

        CouponType couponType = this.couponTypeMapper.selectByPrimaryKey(id);
        if(couponType == null){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return couponType;
    }
}

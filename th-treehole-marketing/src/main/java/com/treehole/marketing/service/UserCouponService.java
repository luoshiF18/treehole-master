package com.treehole.marketing.service;

import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.UserCoupon;
import com.treehole.framework.domain.marketing.bo.CouponBo;
import com.treehole.framework.domain.marketing.bo.UserCouponBo;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.CouponMapper;
import com.treehole.marketing.dao.TypeMapper;
import com.treehole.marketing.dao.UserCouponMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanglu
 */
@Service
public class UserCouponService {

    @Autowired
    private UserCouponMapper userCouponMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CouponService couponService;
    @Autowired
    private TypeMapper typeMapper;

    public QueryResult queryUserCoupons(String userId, Boolean status, Boolean usedStatus) {
        if(StringUtils.isBlank(userId)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setStatus(status);
        userCoupon.setUsedStatus(usedStatus);
        //查询用户优惠券表中该用户的信息
        List<UserCoupon> userCoupons = this.userCouponMapper.select(userCoupon);
        if(CollectionUtils.isEmpty(userCoupons)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        //根据优惠券id查询出优惠券
        List<String> couponIds= userCoupons.stream().map(uCoupon -> uCoupon.getCouponId()).collect(Collectors.toList());
        List<Coupon> coupons = this.couponService.queryCouponByIds(couponIds);
        System.out.println("---------"+coupons.size());
        List<UserCouponBo> userCouponBos = new ArrayList<>();
        for (int i = 0; i < userCoupons.size(); i++) {
            UserCouponBo userCouponBo = new UserCouponBo();
            Coupon coupon = coupons.get(i);
            UserCoupon uCoupon = userCoupons.get(i);
            userCouponBo.setTitle(coupon.getTitle());
            userCouponBo.setIcon(coupon.getIcon());
            userCouponBo.setWithAmount(coupon.getWithAmount());
            userCouponBo.setUsedAmount(coupon.getUsedAmount());
            userCouponBo.setTypeName(this.typeMapper.selectByPrimaryKey(coupon.getTypeId()).getName());
            userCouponBo.setValidStartTime(uCoupon.getValidStartTime());
            userCouponBo.setValidEndTime(uCoupon.getValidEndTime());
            userCouponBo.setStatus(uCoupon.getStatus());
            userCouponBo.setUnUsedStatus(uCoupon.getUnUsedStatus());
            userCouponBo.setUsedStatus(uCoupon.getUsedStatus());
            userCouponBos.add(userCouponBo);
        }
        PageInfo<UserCouponBo> pageInfo = new PageInfo<>(userCouponBos);

        return new QueryResult(userCouponBos, pageInfo.getTotal());
    }

    /**
     * 查询用户未失效的优惠券数量
     * @param userId
     * @return
     */
    public int queryUserCouponCount(String userId) {
        if(StringUtils.isBlank(userId)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setStatus(true);
        return this.userCouponMapper.selectCount(userCoupon);
    }

    @Transactional
    public void saveUserCoupon(CouponBo couponBo, String userId) {

        UserCoupon userCoupon = new UserCoupon();
        if(couponBo == null || StringUtils.isBlank(userId)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //没有库存
        Coupon coupon = this.couponMapper.selectByPrimaryKey(couponBo.getId());
        Integer stock = coupon.getStock();
        if(stock < 1){
            ExceptionCast.cast(MarketingCode.COUPON_DATA_NULL);
        }
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponBo.getId());
        userCoupon.setId(MyNumberUtils.getUUID());
        userCoupon.setCreated(new Date());
        Boolean validTime = couponBo.getValidType();
        if(!validTime){
            Date startTime = new Date();
            userCoupon.setValidStartTime(startTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);//设置起时间
            cal.add(Calendar.DATE, couponBo.getValidDays());
            userCoupon.setValidEndTime(cal.getTime());
            userCoupon.setStatus(true);
            userCoupon.setUnUsedStatus(true);
        } else{
            userCoupon.setValidStartTime(couponBo.getValidStartTime());
            userCoupon.setValidEndTime(couponBo.getValidEndTime());
            userCoupon.setStatus(true);
            if(new Date().getTime()<userCoupon.getValidStartTime().getTime()){
                userCoupon.setUnUsedStatus(false);
            } else {
                userCoupon.setUnUsedStatus(true);
            }
        }
        //减库存
        coupon.setStock(stock - 1);
        if(this.couponMapper.updateByPrimaryKeySelective(coupon) != 1|| this.userCouponMapper.insertSelective(userCoupon) != 1 ){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }


    }
}

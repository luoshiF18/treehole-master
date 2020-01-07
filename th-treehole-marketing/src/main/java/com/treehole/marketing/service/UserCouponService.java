package com.treehole.marketing.service;

import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.UserCoupon;
import com.treehole.framework.domain.marketing.bo.CouponBo;
import com.treehole.framework.domain.marketing.bo.UserCouponBo;
import com.treehole.framework.domain.marketing.request.UserCouponRequest;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.CouponMapper;
import com.treehole.marketing.dao.UserCouponMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    private StringRedisTemplate redisTemplate;
   // @Autowired
   // private TypeMapper typeMapper;

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
      //  List<String> couponIds= userCoupons.stream().map(uCoupon -> uCoupon.getCouponId()).collect(Collectors.toList());
    //    List<Coupon> coupons = this.couponService.queryCouponByIds(couponIds);
      //  System.out.println("---------"+coupons.size());
        List<UserCouponBo> userCouponBos = new ArrayList<>();
        for (int i = 0; i < userCoupons.size(); i++) {
            UserCouponBo userCouponBo = new UserCouponBo();
         //   Coupon coupon = coupons.get(i);
            UserCoupon uCoupon = userCoupons.get(i);
            userCouponBo.setId(uCoupon.getId());
            userCouponBo.setTitle(uCoupon.getCouponTitle());
            userCouponBo.setIcon(uCoupon.getIcon());
            userCouponBo.setWithAmount(uCoupon.getWithAmount());
            userCouponBo.setUsedAmount(uCoupon.getUsedAmount());
          //  userCouponBo.setTypeName(this.typeMapper.selectByPrimaryKey(coupon.getTypeId()).getName());
            userCouponBo.setValidStartTime(uCoupon.getValidStartTime());
            userCouponBo.setValidEndTime(uCoupon.getValidEndTime());
            userCouponBo.setStatus(uCoupon.getStatus());
            userCouponBo.setUnUsedStatus(uCoupon.getUnUsedStatus());
            userCouponBo.setUsedStatus(uCoupon.getUsedStatus());
            userCouponBo.setUsedTypeName(uCoupon.getUsedType());
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
    public void saveUserCoupon(UserCouponRequest userCouponRequest) {
        if(StringUtils.isBlank(userCouponRequest.getId()) || StringUtils.isBlank(userCouponRequest.getUserId())){
            System.out.println(userCouponRequest.getId()+ "///" + userCouponRequest.getUserId());
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //限领
        //数据库中读
        UserCoupon uc = new UserCoupon();
        uc.setCouponId(userCouponRequest.getId());
        uc.setUserId(userCouponRequest.getUserId());
        uc.setStatus(true);
        List<UserCoupon> select = this.userCouponMapper.select(uc);
        if(select.size() >= userCouponRequest.getLimitNum()){
            ExceptionCast.cast(MarketingCode.RECEIVE_ERROR);
        }

        //没有库存
        Coupon coupon = this.couponMapper.selectByPrimaryKey(userCouponRequest.getId());
        int stock = coupon.getStock();
        if(stock < 1){
            ExceptionCast.cast(MarketingCode.STOCK_NULL);
        }

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userCouponRequest.getUserId());
        userCoupon.setCouponId(userCouponRequest.getId());
        userCoupon.setId(MyNumberUtils.getUUID());
        userCoupon.setCreated(new Date());
        userCoupon.setCouponTitle(userCouponRequest.getTitle());
        userCoupon.setIcon(userCouponRequest.getIcon());
        userCoupon.setWithSpecial(userCouponRequest.getWithSpecial());
        userCoupon.setWithAmount(userCouponRequest.getWithAmount());
        userCoupon.setUsedAmount(userCouponRequest.getUsedAmount());
        userCoupon.setUsedType(userCouponRequest.getUsedType());///////
        Boolean validTime = userCouponRequest.getValidType();
        //用户领取到的优惠券的有效使用期限
        if(!validTime){
            //相对期限为领取时+固定天数
            Date startTime = new Date();
            userCoupon.setValidStartTime(startTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);//设置起时间
            cal.add(Calendar.DATE, userCouponRequest.getValidDays());
            userCoupon.setValidEndTime(cal.getTime());
            userCoupon.setStatus(true);
            userCoupon.setUnUsedStatus(true);
            //存入redis 过期时间
            //格式为id_coupon_ONGOING ,值目前觉得无所谓，所以存为了id
            redisTemplate.boundValueOps(userCoupon.getId()+"_userCoupon"+"_VALID_UNUSED").set(userCoupon.getId());
            redisTemplate.boundValueOps(userCoupon.getId()+"_userCoupon"+"_VALID_UNUSED").expire(userCoupon.getValidEndTime().getTime() - (new Date().getTime()), TimeUnit.MILLISECONDS);

        } else{
            userCoupon.setValidStartTime(userCouponRequest.getValidStartTime());
            userCoupon.setValidEndTime(userCouponRequest.getValidEndTime());
            userCoupon.setStatus(true);
            if(new Date().getTime()<userCoupon.getValidStartTime().getTime()){
                userCoupon.setUnUsedStatus(false);//不生效
                redisTemplate.boundValueOps(userCoupon.getId()+"_userCoupon"+"_INVALID_UNUSED").set(userCoupon.getId());
                redisTemplate.boundValueOps(userCoupon.getId()+"_userCoupon"+"_INVALID_UNUSED").expire((userCoupon.getValidStartTime().getTime() - new Date().getTime()),TimeUnit.MILLISECONDS);
            } else {
                userCoupon.setUnUsedStatus(true);//生效
                redisTemplate.boundValueOps(userCoupon.getId()+"_userCoupon"+"_VALID_UNUSED").set(userCoupon.getId());
                redisTemplate.boundValueOps(userCoupon.getId()+"_userCoupon"+"_VALID_UNUSED").expire(userCoupon.getValidEndTime().getTime() - (new Date().getTime()), TimeUnit.MILLISECONDS);
            }
        }
        //减库存
        coupon.setStock(stock - 1);
        if(this.couponMapper.updateByPrimaryKeySelective(coupon) != 1|| this.userCouponMapper.insertSelective(userCoupon) != 1 ){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }


    }

    /**
     * 改变用户优惠券状态
     * @param id
     * @param ifValid
     * @param ifUsed
     */
    @Transactional
    public void changeStatus(String id, String ifValid, String ifUsed) {

        UserCoupon userCoupon = this.userCouponMapper.selectByPrimaryKey(id);
        userCoupon.setId(id);
        //过期优惠券的状态是 --- 已失效，失效中：已过期
        if("VALID".equals(ifValid)){
            userCoupon.setStatus(false);
            if("UNUSED".equals(ifUsed)){
                userCoupon.setUsedStatus(false);//已过期
                userCoupon.setUnUsedStatus(null);//这里设置未使用状态为null不知道有无意义
            }
        } else if("INVALID".equals(ifValid)){//未生效优惠券过期则代表已经生效
            redisTemplate.boundValueOps(id+"_userCoupon"+"_VALID_UNUSED").set(id);
            redisTemplate.boundValueOps(id+"_userCoupon"+"_VALID_UNUSED").expire(userCoupon.getValidEndTime().getTime() - (new Date().getTime()), TimeUnit.MILLISECONDS);
            userCoupon.setUnUsedStatus(true);
        }
        this.userCouponMapper.updateByPrimaryKey(userCoupon);//

    }

    @Transactional
    public void changeUsedStatusToTrue(String id){
        UserCoupon userCoupon = this.userCouponMapper.selectByPrimaryKey(id);
        if(userCoupon.getStatus() == true){
            //有效的优惠券状态分别改为---已使用，已失效，未使用的状态想设为null
            userCoupon.setUsedStatus(true);
            userCoupon.setStatus(false);
            userCoupon.setUnUsedStatus(null);
            redisTemplate.delete(id+"_userCoupon_VALID_UNUSED");
        } else {
            ExceptionCast.cast(MarketingCode.COUPON_INVALID);
        }

        if(this.userCouponMapper.updateByPrimaryKey(userCoupon) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }
    }


}

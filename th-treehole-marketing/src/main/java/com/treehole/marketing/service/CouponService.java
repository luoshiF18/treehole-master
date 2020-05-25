package com.treehole.marketing.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.CouponType;
import com.treehole.framework.domain.marketing.InteractiveActivity;
import com.treehole.framework.domain.marketing.bo.CouponBo;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.domain.marketing.response.StatisticsData;
import com.treehole.framework.domain.marketing.utils.MyStatusCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.CouponMapper;
import com.treehole.marketing.dao.CouponTypeMapper;
import com.treehole.marketing.utils.MyChineseCharUtil;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wanglu
 */
@Service
public class CouponService {
    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponTypeMapper couponTypeMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;



    /**
     * 根据查询条件分页并排序查询coupon信息
     * @param key
     * @param page
     * @param size
     * @param sortBy
     * @param desc
     * @return
     */
    public QueryResult queryCouponByPage(String key, Integer page, Integer size, String sortBy, Boolean desc,Integer status,Integer notStatus) {
        Example example = new Example(Coupon.class);
        Example.Criteria criteria = example.createCriteria();
        //条件不为空，则添加查询条件
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }
        if(status != null){
            criteria.andEqualTo("status", status);
        }
        //
        if(notStatus != null){
            criteria.andNotEqualTo("status", notStatus);
        }

        // 添加分页条件
        PageHelper.startPage(page, size);
        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        //查询
        List<Coupon> coupons = this.couponMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(coupons)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }

        for(Coupon coupon:coupons){
           /* coupon.setLetter(StringUtils.substring(coupon.getLetter(), 0, 1));
            coupon.setValidTypeName(coupon.getValidType() ? "绝对时效":"相对时效");
            Type type = this.typeMapper.selectByPrimaryKey(coupon.getTypeId());
            coupon.setTypeName(type.getName());*/
           this.transfer(coupon);
        }
        // 包装成pageInfo
        PageInfo<Coupon> pageInfo = new PageInfo<>(coupons);

        return new QueryResult(coupons, pageInfo.getTotal());
    }
    //将数据库中存的值转给字符串形式展示在前端
    private void transfer(Coupon coupon){
        coupon.setLetter(StringUtils.substring(coupon.getLetter(), 0, 1));
        coupon.setValidTypeName(coupon.getValidType() ? "绝对时效":"相对时效");
        CouponType type = this.couponTypeMapper.selectByPrimaryKey(coupon.getUsedTypeId());
        coupon.setUsedTypeName(type.getUsedType());
        if(coupon.getType() == 1){
            coupon.setTypeName("注册赠券");
        } else if(coupon.getType() == 2){
            coupon.setTypeName("全场赠券");
        } else if(coupon.getType() == 3){
            coupon.setTypeName("购物赠券");
        }
        if(MyStatusCode.STATUS_FINISHED.equals(coupon.getStatus())){
            coupon.setStatusName("已结束");
        } else if (MyStatusCode.STATUS_NOT_STARTED.equals(coupon.getStatus())){
            coupon.setStatusName("未开始");
        } else if (MyStatusCode.STATUS_ONGOING.equals(coupon.getStatus())){
            coupon.setStatusName("进行中");
        }
    }

    /**
     * 根据优惠券id查询优惠券信息
     * @param id
     * @return
     */
    public Coupon queryCouponById(String id) {
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        if(coupon == null){
            ExceptionCast.cast(MarketingCode.DATA_NULL);
        }
        this.transfer(coupon);
        return coupon;
    }

    /**
     * 用户可以查看到的优惠券信息
     * @param cid
     * @return
     */
    public CouponBo queryCouponBoById(String cid) {
        if(StringUtils.isBlank(cid)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
       /* Coupon coupon = couponMapper.selectByPrimaryKey(cid);
        if(coupon == null){
            ExceptionCast.cast(MarketingCode.COUPON_DATA_NULL);
        }*/
        Coupon coupon = this.queryCouponById(cid);
        CouponBo couponBo = new CouponBo();
        couponBo.setId(coupon.getId());
        couponBo.setTitle(coupon.getTitle());
        couponBo.setIcon(coupon.getIcon());
        couponBo.setTypeName(coupon.getTypeName());
        //
        CouponType type = this.couponTypeMapper.selectByPrimaryKey(coupon.getUsedTypeId());
        couponBo.setUsedType(type.getUsedType());
        couponBo.setWithSpecial(coupon.getWithSpecial());
        couponBo.setWithAmount(coupon.getWithAmount());
        couponBo.setUsedAmount(coupon.getUsedAmount());
        couponBo.setValidType(coupon.getValidType());
        couponBo.setValidStartTime(coupon.getValidStartTime());
        couponBo.setValidEndTime(coupon.getValidEndTime());
        couponBo.setValidDays(coupon.getValidDays());
        couponBo.setUsedBy(couponBo.getUsedBy());
        couponBo.setStatus(coupon.getStatus());
        couponBo.setLimitNum(coupon.getLimitNum());
        return couponBo;
    }
    /**
     * 根据优惠券ids查询优惠券信息
     * @param ids
     * @return
     */
    public List<Coupon> queryCouponByIds(List<String> ids){
        List<Coupon> coupons = new ArrayList<>();
        ids.forEach(id -> {
            coupons.add(this.couponMapper.selectByPrimaryKey(id)) ;
        });
        return coupons;
    }
    /**
     * 添加优惠券
     * @param coupon
     */
    public void saveCoupon(Coupon coupon) {
        if(this.queryCountOfValidCoupon() > 10){
            ExceptionCast.cast(MarketingCode.COUPON_NUM_TOMUCH);
        }
        //数据为空
        if(StringUtils.isBlank(coupon.getTitle())|| coupon.getStartTime() == null ||
                coupon.getEndTime() == null || coupon.getQuota() == null ||
                coupon.getUsedAmount() == null || coupon.getUsedAmount() == new BigDecimal(0)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //优惠券有效期
        if((coupon.getValidType() == false && coupon.getValidDays() == null)||
                (coupon.getValidType()==true && (coupon.getValidStartTime() == null ||
                        coupon.getValidEndTime() == null))){
            ExceptionCast.cast(MarketingCode.COUPON_VALID_DATE_ERROR);
        }
        //手动设置数据
        coupon.setId(MyNumberUtils.getUUID());
        String upperCase = MyChineseCharUtil.getUpperCase(coupon.getTitle(), false);
        coupon.setLetter(upperCase);
        coupon.setStock(coupon.getQuota());    //库存=发放量
        coupon.setCreated(new Date());
        coupon.setUpdated(coupon.getCreated()); //首次更新时间=创建时间
        //添加redis操作对优惠券状态进行修改
        Date date = new Date();
        Date startTime = coupon.getStartTime();
        Date endTime = coupon.getEndTime();
        //活动开始时间小于当前时间则已开始
        if(startTime.compareTo(date) < 0
                && endTime.compareTo(new Date())>0){
            //格式为id_coupon_ONGOING ,值无所谓，存为id
            redisTemplate.boundValueOps(
                    coupon.getId()+"_coupon"+"_ONGOING").set(coupon.getId());
            redisTemplate.boundValueOps(coupon.getId()+"_coupon"+"_ONGOING")
                    .expire(endTime.getTime() - (new Date().getTime()), TimeUnit.MILLISECONDS);
            coupon.setStatus(2);
        } else if(startTime.compareTo(date) > 0 ){//开始时间大于当前时间，未开始
            redisTemplate.boundValueOps(
                    coupon.getId()+"_coupon"+"_NOTSTARTED").set(coupon.getId());
            redisTemplate.boundValueOps(coupon.getId()+"_coupon"+"_NOTSTARTED")
                    .expire((startTime.getTime() - new Date().getTime()),TimeUnit.MILLISECONDS);
            coupon.setStatus(1);
        }
        //添加
        if(this.couponMapper.insertSelective(coupon) != 1 ){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }
    }

    /**
     * 更改优惠券信息
     * @param coupon
     */
    @Transactional
    public void updateCouponInfo(Coupon coupon) {
        if(StringUtils.isBlank(coupon.getId())){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Coupon c = this.couponMapper.selectByPrimaryKey(coupon.getId());

        //数据为空
        if(StringUtils.isBlank(coupon.getTitle())||
                coupon.getStartTime() == null ||
                coupon.getEndTime() == null ||
                coupon.getQuota() == null ||
                coupon.getUsedAmount() == null ||
                coupon.getUsedAmount() == new BigDecimal(0)||
                coupon.getQuota() < c.getQuota()||
                coupon.getStartTime().compareTo(c.getStartTime()) != 0||
                coupon.getEndTime().compareTo(c.getEndTime()) != 0){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        String upperCase = MyChineseCharUtil.getUpperCase(coupon.getTitle(), false);
        coupon.setCreated(c.getCreated());
        coupon.setLetter(upperCase);
        coupon.setUpdated(new Date());
        //发行量只增不减，库存为发行量-领取量，发行金额进行修改
       // Coupon oldCoupon = this.couponMapper.selectByPrimaryKey(coupon.getId());
       // if(oldCoupon.getQuota() > coupon.getQuota()){
       //     ExceptionCast.cast(MarketingCode.UPDATE_DATA_ERROR);
       // }

        //如果时效改了，那之前的数据应该为null，
        if(coupon.getValidType() == false){
            coupon.setValidStartTime(null);
            coupon.setValidEndTime(null);
        } else {
            coupon.setValidDays(null);
        }

        if(this.couponMapper.updateByPrimaryKey(coupon) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }
    }

    /**
     * 将优惠券发放结束
     * @param id
     */
    @Transactional
    public void updateStatusToFinished(String id){
        //数据为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Coupon coupon = this.couponMapper.selectByPrimaryKey(id);
        //正在进行中的状态修改为结束，其他状态返回禁止修改操作的消息
        if(MyStatusCode.STATUS_ONGOING.equals(coupon.getStatus())){
            coupon.setStatus(MyStatusCode.STATUS_FINISHED);
            if(this.couponMapper.updateByPrimaryKeySelective(coupon) == 1) {
                redisTemplate.delete(id+"_coupon_ONGOING");

            } else {
                ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
            }

        } else {
            ExceptionCast.cast(MarketingCode.UPDATE_FORBIDDEN);
        }


    }

    /**
     * 删除优惠券，正在进行中的优惠券不可删除，只能结束或者修改
     * @param id
     */
    @Transactional
    public void deleteCouponById(String id) {
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Coupon coupon = this.couponMapper.selectByPrimaryKey(id);
        if(MyStatusCode.STATUS_ONGOING.equals(coupon.getStatus())){//正在进行发放的优惠券要先下架才能删除
            ExceptionCast.cast(MarketingCode.DELETE_FORBIDDEN);
        }
        //删除优惠券及Redis中的信息
        try {

                this.couponMapper.deleteByPrimaryKey(id);
                if(MyStatusCode.STATUS_NOT_STARTED.equals(coupon.getStatus())){
                    redisTemplate.delete(id+"_coupon__NOTSTARTED");
                }
        } catch (Exception e) {

            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
            e.printStackTrace();
        }
    }

    @Transactional
    public void changeStatus(String status, String id){
        Coupon coupon = new Coupon();
        coupon.setId(id);
        if("ONGOING".equals(status)){
            coupon.setStatus(MyStatusCode.STATUS_FINISHED);
        }else if("NOTSTARTED".equals(status)){
            coupon = this.couponMapper.selectByPrimaryKey(id);//需要时间，所以再查找
            coupon.setStatus(MyStatusCode.STATUS_ONGOING);
            redisTemplate.boundValueOps(id+"_coupon_ONGOING").set(id);
            redisTemplate.boundValueOps(id+"_coupon_ONGOING").expire((coupon.getEndTime().getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
        }
        this.couponMapper.updateByPrimaryKeySelective(coupon);
    }


    public int queryCountOfValidCoupon(){
        Example example = new Example(Coupon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("status", 0);
        return this.couponMapper.selectCountByExample(example);
    }

    public QueryResult queryValidCoupon(String today) {
        if(today == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
      /*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today_= null;
        try {
            today_ = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        List<Coupon> coupons = this.couponMapper.queryValidCoupon(today);
        if(CollectionUtils.isEmpty(coupons)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        List<CouponBo> couponBos = new ArrayList<>();
        for(Coupon coupon: coupons){
            this.transfer(coupon);

            CouponBo couponBo = new CouponBo();
            couponBo.setId(coupon.getId());
            couponBo.setTitle(coupon.getTitle());
            couponBo.setIcon(coupon.getIcon());
            couponBo.setTypeName(coupon.getTypeName());
            //
            CouponType type = this.couponTypeMapper.selectByPrimaryKey(coupon.getUsedTypeId());
            System.out.println(type+"///"+coupon.getUsedTypeId());
            couponBo.setUsedType(type.getUsedType());
            couponBo.setWithSpecial(coupon.getWithSpecial());
            couponBo.setWithAmount(coupon.getWithAmount());
            couponBo.setUsedAmount(coupon.getUsedAmount());
            couponBo.setValidType(coupon.getValidType());
            couponBo.setValidStartTime(coupon.getValidStartTime());
            couponBo.setValidEndTime(coupon.getValidEndTime());
            couponBo.setValidDays(coupon.getValidDays());
            couponBo.setUsedBy(couponBo.getUsedBy());
            couponBo.setStatus(coupon.getStatus());
            couponBo.setLimitNum(coupon.getLimitNum());
            couponBos.add(couponBo);
        }
        return new QueryResult<>(couponBos, couponBos.size());
    }

    /**
     * 查找某个优惠券的统计信息
     * @param id
     * @return
     */
    public String querySomeCouponStock(String id) {
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }

        List<StatisticsData> couponData = this.couponMapper.querySomeCouponStock(id);
        //将list<PieData> 转换为json格式 返给前端
        String data = JSON.toJSONString(couponData);
        System.out.println(data);
        return  data;
    }
}

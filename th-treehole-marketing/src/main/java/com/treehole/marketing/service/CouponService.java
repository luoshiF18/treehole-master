package com.treehole.marketing.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.dto.CouponDTO;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.CouponMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wanglu
 */
@Service
public class CouponService {
    @Autowired
    private CouponMapper couponMapper;

    /**
     * 根据查询条件分页并排序查询coupon信息
     * @param key
     * @param page
     * @param size
     * @param sortBy
     * @param desc
     * @return
     */
    public QueryResult queryCouponByPage(String key, Integer page, Integer size, String sortBy, Boolean desc) {
        Example example = new Example(Coupon.class);
        Example.Criteria criteria = example.createCriteria();
        //条件不为空，则添加查询条件
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }
        //查询的优惠券是有效的
        criteria.andEqualTo("valid", 1);
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
        //!!!!!!!!!此处应该设为null，还是返回前端的属性中不该有valid？
        coupons.forEach(coupon->{
            coupon.setValid(null);
        });
        // 包装成pageInfo
        PageInfo<Coupon> pageInfo = new PageInfo<>(coupons);

        return new QueryResult(coupons, pageInfo.getTotal());
    }

    /**
     * 根据优惠券id查询优惠券信息
     * @param cid
     * @return
     */
    public Coupon queryCouponById(String cid) {
        if(StringUtils.isBlank(cid)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //!!!!!!!!!此处应该设为null，还是返回前端的属性中不该有valid？
        Coupon coupon = this.couponMapper.selectByPrimaryKey(cid);
        coupon.setValid(null);
        return coupon;
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
     * @param couponDTO
     */
    public void saveCoupon(CouponDTO couponDTO) {
        //数据为空
        if(couponDTO == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponDTO, coupon);
        //!!!!!!!!!不确定还有没有字段需要修改
        coupon.setId(MyNumberUtils.getUUID());
        coupon.setValid(true);
        coupon.setCreated(new Date());
        coupon.setUpdated(coupon.getCreated());
        coupon.setStock(coupon.getQuota());//库存量值为发行量
        if(this.couponMapper.insertSelective(coupon) != 1 ){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }


    }

    /**
     * 更改优惠券信息
     * @param coupon
     */
    @Transactional
    public void updateCouponInfo(CouponDTO couponDTO) {
        //数据为空
        if(couponDTO == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponDTO, coupon);
        coupon.setCreated(null);
        coupon.setUpdated(new Date());
        coupon.setValid(null);
        if(this.couponMapper.updateByPrimaryKeySelective(coupon) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }
    }

    /**
     * 删除优惠券，将删除状态码置为0
     * @param cid
     */
    @Transactional
    public void deleteCouponById(String cid) {
        Coupon coupon = this.queryCouponById(cid);
        coupon.setValid(false);
        if(this.couponMapper.updateByPrimaryKeySelective(coupon) != 1){
            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
        }
    }
}

package com.treehole.marketing.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.*;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.domain.marketing.utils.MyStatusCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.InteractiveActivityMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wanglu
 */
@Service
public class InteractiveActivityService {

    @Autowired
    private InteractiveActivityMapper interactiveActivityMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据条件分页查询活动
     * @param key
     * @param status
     * @param beginTime
     * @param endTime
     * @param page
     * @param size
     * @param sortBy
     * @param desc
     * @return
     */
    public QueryResult<InteractiveActivity> queryActivityByPage(String key, Integer status, Date beginTime, Date endTime, Integer page, Integer size, String sortBy, Boolean desc) {
        //
        Example example = new Example(InteractiveActivity.class);
        Example.Criteria criteria = example.createCriteria();
        //条件不为空，则添加查询条件
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }
        if(status != null){
            criteria.andEqualTo("status", status);
        }
        if(beginTime != null){
            criteria.andEqualTo("beginTime", beginTime);
        }
        if(endTime != null){
            criteria.andEqualTo("endTime", endTime);
        }
        // 添加分页条件
        PageHelper.startPage(page, size);

        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        //查询
        List<InteractiveActivity> activities = this.interactiveActivityMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(activities)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }

        for(InteractiveActivity activity: activities){
            this.transferActivity(activity);
        }
        PageInfo<InteractiveActivity> pageInfo = new PageInfo<>(activities);
        return new QueryResult(activities, pageInfo.getTotal());
    }

    private void transferActivity(InteractiveActivity activity) {

        int status = activity.getStatus();
        if(status == MyStatusCode.STATUS_FINISHED){
            activity.setStatusName("已结束");
        } else if (status == MyStatusCode.STATUS_NOT_STARTED){
            activity.setStatusName("未开始");
        } else if (status == MyStatusCode.STATUS_ONGOING){
            activity.setStatusName("进行中");
        }
    }

    public InteractiveActivity queryActivityById(String id) {
        //为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //根据主键查找
        InteractiveActivity activity = this.interactiveActivityMapper.selectByPrimaryKey(id);
        if(activity == null){
            ExceptionCast.cast(MarketingCode.DATA_NULL);
        }
        return activity;
    }

    @Transactional
    @CacheEvict(value = "ReleaseInteractiveActivity", allEntries = true)
    public void saveActivity(InteractiveActivity interactiveActivity) {
        if(interactiveActivity.getReleaseBeginTime() == null ||
        interactiveActivity.getReleaseEndTime() == null ||
        interactiveActivity.getTitle() == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }

        interactiveActivity.setId(MyNumberUtils.getUUID());
        //过期时间存入redis
        Date date = new Date();
        Date beginTime = interactiveActivity.getBeginTime();
        Date endTime = interactiveActivity.getEndTime();
        //活动开始时间小于当前时间，并且结束时间大于当前时间
        if(beginTime.compareTo(date) < 0 && endTime.compareTo(new Date())>0){
            //格式为id_coupon_ONGOING ,值目前觉得无所谓，所以存为了id
            redisTemplate.boundValueOps(interactiveActivity.getId()+"_interactiveActivity"+"_ONGOING").set(interactiveActivity.getId());
            redisTemplate.boundValueOps(interactiveActivity.getId()+"_interactiveActivity"+"_ONGOING").expire(endTime.getTime() - (new Date().getTime()), TimeUnit.MILLISECONDS);
            interactiveActivity.setStatus(MyStatusCode.STATUS_ONGOING);
        }else if(beginTime.compareTo(date) > 0 ){//开始时间大于当前时间，未开始
            redisTemplate.boundValueOps(interactiveActivity.getId()+"_interactiveActivity"+"_NOTSTARTED").set(interactiveActivity.getId());
            redisTemplate.boundValueOps(interactiveActivity.getId()+"_interactiveActivity"+"_NOTSTARTED").expire((beginTime.getTime() - new Date().getTime()),TimeUnit.MILLISECONDS);
            interactiveActivity.setStatus(MyStatusCode.STATUS_NOT_STARTED);

        }
        interactiveActivity.setCreated(new Date());
        interactiveActivity.setUpdated(interactiveActivity.getCreated());
        if(this.interactiveActivityMapper.insertSelective(interactiveActivity) != 1){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }

    }
    @Transactional
    @CacheEvict(value = "ReleaseInteractiveActivity", allEntries = true)
    public void updateActivityInfo(InteractiveActivity interactiveActivity) {
        if(interactiveActivity.getReleaseBeginTime() == null ||
                interactiveActivity.getReleaseEndTime() == null ||
                interactiveActivity.getTitle() == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //避免传过来创建时间
        interactiveActivity.setCreated(this.interactiveActivityMapper.selectByPrimaryKey(interactiveActivity).getCreated());
        interactiveActivity.setUpdated(new Date());
        //
        if(interactiveActivity.getIfOnline()){
            interactiveActivity.setPlace(null);
        }
        if(!interactiveActivity.getIfLimitNum()){
            interactiveActivity.setLimitNum(null);
        }
        if(!interactiveActivity.getIfPay()){
            interactiveActivity.setPayAmount(null);
        }
        if(this.interactiveActivityMapper.updateByPrimaryKeySelective(interactiveActivity) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }


    }
    @Transactional
    @CacheEvict(value = "ReleaseInteractiveActivity", allEntries = true)
    public void deleteActivityById(String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                //进行中的活动不允许删除
                InteractiveActivity activity = this.interactiveActivityMapper.selectByPrimaryKey(id);
                if(MyStatusCode.STATUS_ONGOING.equals(activity.getStatus())){
                    ExceptionCast.cast(MarketingCode.DELETE_FORBIDDEN);
                }
                //删
                this.interactiveActivityMapper.deleteByPrimaryKey(id);
                if(MyStatusCode.STATUS_NOT_STARTED.equals(activity.getStatus())){
                    redisTemplate.delete(id+"_interactiveActivity__NOTSTARTED");
                }

            }
        } catch (Exception e) {
            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
            e.printStackTrace();
        }

    }

    @Cacheable(value = "ReleaseInteractiveActivity")
    public QueryResult<InteractiveActivity> queryActivityByReleaseTime(String today) {
        if(StringUtils.isBlank(today)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        List<InteractiveActivity> interactiveActivities = this.interactiveActivityMapper.queryActivityByReleaseTime(today);
        if(CollectionUtils.isEmpty(interactiveActivities)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return new QueryResult<>(interactiveActivities, interactiveActivities.size());
    }


    @Transactional
    @CacheEvict(value = "ReleaseInteractiveActivity", allEntries = true)
    public void changeStatus(String status, String id){
        InteractiveActivity activity = new InteractiveActivity();
        activity.setId(id);
        if("ONGOING".equals(status)){
            activity.setStatus(MyStatusCode.STATUS_FINISHED);
        }else if("NOTSTARTED".equals(status)){
            activity = this.interactiveActivityMapper.selectByPrimaryKey(id);
            activity.setStatus(MyStatusCode.STATUS_ONGOING);
            redisTemplate.boundValueOps(id+"_interactiveActivity_ONGOING").set(id);
            redisTemplate.boundValueOps(id+"_interactiveActivity_ONGOING").expire((activity.getEndTime().getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
        }
        this.interactiveActivityMapper.updateByPrimaryKeySelective(activity);
    }

    /**
     * 将活动结束
     * @param id
     */
    @Transactional
    @CacheEvict(value = "ReleaseInteractiveActivity", allEntries = true)
    public void updateStatusToFinished(String id){
        //数据为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        InteractiveActivity activity = this.interactiveActivityMapper.selectByPrimaryKey(id);
        //正在进行中的状态修改为结束，其他状态返回禁止修改操作的消息
        if(MyStatusCode.STATUS_ONGOING.equals(activity.getStatus())){
            activity.setStatus(MyStatusCode.STATUS_FINISHED);
            if(this.interactiveActivityMapper.updateByPrimaryKeySelective(activity) == 1) {
                redisTemplate.delete(id+"_interactiveActivity_ONGOING");

            } else {
                ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
            }

        } else {
            ExceptionCast.cast(MarketingCode.UPDATE_FORBIDDEN);
        }


    }
}

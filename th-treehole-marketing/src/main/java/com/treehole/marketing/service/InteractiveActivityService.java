package com.treehole.marketing.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.InteractiveActivity;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.InteractiveActivityMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author wanglu
 */
@Service
public class InteractiveActivityService {

    @Autowired
    private InteractiveActivityMapper interactiveActivityMapper;


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
        PageInfo<InteractiveActivity> pageInfo = new PageInfo<>(activities);
        return new QueryResult(activities, pageInfo.getTotal());
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
    public void saveActivity(InteractiveActivity interactiveActivity) {
        if(interactiveActivity.getReleaseBeginTime() == null ||
        interactiveActivity.getReleaseEndTime() == null ||
        interactiveActivity.getTitle() == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }

        interactiveActivity.setId(MyNumberUtils.getUUID());
        if(this.interactiveActivityMapper.insertSelective(interactiveActivity) != 1){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }

    }
    @Transactional
    public void updateActivityInfo(InteractiveActivity interactiveActivity) {
    }
    @Transactional
    public void deleteActivityById(String id) {

    }
    public QueryResult<InteractiveActivity> queryActivityByReleaseTime(Date today) {
        if(today == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Example example = new Example(InteractiveActivity.class);
        example.createCriteria().andLessThanOrEqualTo("releaseBeginTime",today);
        example.createCriteria().andGreaterThan("releaseEndTime", today);
        example.setOrderByClause("release_begin_time" + " " + "desc");
        List<InteractiveActivity> interactiveActivities = this.interactiveActivityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(interactiveActivities)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return new QueryResult<>(interactiveActivities, interactiveActivities.size());
    }
}

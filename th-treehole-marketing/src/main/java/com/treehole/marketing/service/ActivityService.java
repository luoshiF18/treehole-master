package com.treehole.marketing.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.ActivityMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;


    public QueryResult<Activity> queryActivityByPage(String key, Integer status, Date beginTime, Date endTime, Integer page, Integer rows, String sortBy, Boolean desc) {
        //
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        //条件不为空，则添加查询条件
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }
        if(status != null){
            criteria.andEqualTo("status", status);
        }
        if(beginTime != null){
            criteria.andEqualTo("begin_time", beginTime);
        }
        if(endTime != null){
            criteria.andEqualTo("end_time", endTime);
        }
        //查询的活动是有效的
        criteria.andEqualTo("valid", 1);
        // 添加分页条件
        PageHelper.startPage(page, rows);

        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        //查询
        List<Activity> activities = this.activityMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(activities)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);
        return new QueryResult(activities, pageInfo.getTotal());
    }

    /**
     * 根据id查询活动详情
     * @param id
     * @return
     */
    public Activity queryActivityById(String id) {
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //!!!!!!!!!此处应该设为null，还是返回前端的属性中不该有valid？
        Activity activity = this.activityMapper.selectByPrimaryKey(id);
       // activity.setValid(null);
        return activity;
    }

    @Transactional
    public void saveActivity(Activity activity) {
        //数据为空
        if(activity == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        activity.setId(MyNumberUtils.getUUID());
        activity.setCreated(new Date());
        activity.setUpdated(activity.getCreated());
        if(this.activityMapper.insertSelective(activity) != 1){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }
    }

    @Transactional
    public void updateActivityInfo(Activity activity) {
        //数据为空
        if(activity == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }

        //activity.setCreated(null);不能这样子设置空值
        activity.setUpdated(new Date());
        if(this.activityMapper.updateByPrimaryKeySelective(activity) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }
    }
    @Transactional
    public void deleteActivityById(String id) {
        Activity activity = this.queryActivityById(id);
        if(this.activityMapper.updateByPrimaryKeySelective(activity) != 1){
            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
        }
    }
}

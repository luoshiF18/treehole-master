package com.treehole.marketing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.ActivityGoods;
import com.treehole.framework.domain.marketing.ActivityRule;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.request.ActivityRequest;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.domain.marketing.utils.MyStatusCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.ActivityGoodsMapper;
import com.treehole.marketing.dao.ActivityMapper;
import com.treehole.marketing.dao.ActivityRuleMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wanglu
 */
@Service
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static  final ObjectMapper MAPPER = new ObjectMapper();
    //@Autowired
    //private TypeMapper typeMapper;

    public QueryResult<Activity> queryActivityByPage(String key, Integer status, Date beginTime, Date endTime, Integer page, Integer rows, String sortBy, Boolean desc, String typeId) {
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
            criteria.andEqualTo("beginTime", beginTime);
        }
        if(endTime != null){
            criteria.andEqualTo("endTime", endTime);
        }
        //实体属性
        if(typeId != null){
            criteria.andLike("typeId", "%"+typeId);

        }
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
        for(Activity activity: activities){
            this.transferActivity(activity);
        }
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);
        return new QueryResult(activities, pageInfo.getTotal());
    }

    private void transferActivity(Activity activity){
        /*Type type = this.typeMapper.selectByPrimaryKey(activity.getTypeId());
        activity.setTypeName(type.getName());*/
        String typeId = activity.getTypeId();
        if("4_4_0".equals(typeId)){
            activity.setTypeName("签到");
        }else if("5_4_0".equals(typeId)){
            activity.setTypeName("送积分");
        }else if("1_1_1".equals(typeId)){
            activity.setTypeName("满减");
        }else if("2_2_1".equals(typeId)){
            activity.setTypeName("打折");
        }else if("3_3_1".equals(typeId)){
            activity.setTypeName("促销");
        }
        int status = activity.getStatus();
        if(status == MyStatusCode.STATUS_FINISHED){
            activity.setStatusName("已结束");
        } else if (status == MyStatusCode.STATUS_NOT_STARTED){
            activity.setStatusName("未开始");
        } else if (status == MyStatusCode.STATUS_ONGOING){
            activity.setStatusName("进行中");
        }

    }

    /**
     * 根据id查询活动详情
     * @param id
     * @return
     */
    public Activity queryActivityById(String id) {
        //为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        //根据主键查找
        Activity activity = this.activityMapper.selectByPrimaryKey(id);
        if(activity == null){
            ExceptionCast.cast(MarketingCode.DATA_NULL);
        }
        this.transferActivity(activity);
        return activity;
    }


    @Transactional
    public void saveActivity(ActivityRequest activityRequest) {
        Activity activity = activityRequest.getActivity();
        ActivityRule activityRule = activityRequest.getActivityRule();
        List<ActivityGoods> activityGoodsList = activityRequest.getActivityGoodsList();
        //数据为空
        if(activity == null|| activity.getEndTime().compareTo(new Date())<0){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        activity.setId(MyNumberUtils.getUUID());
        //放在这里是否合适!!!!!!!
        Date date = new Date();
        Date beginTime = activity.getBeginTime();
        Date endTime = activity.getEndTime();
        //活动开始时间小于当前时间，并且结束时间大于当前时间
        if(beginTime.compareTo(date) < 0 && endTime.compareTo(new Date())>0){
            //格式为id_coupon_ONGOING ,值目前觉得无所谓，所以存为了id
            redisTemplate.boundValueOps(activity.getId()+"_activity"+"_ONGOING").set(activity.getId());
            redisTemplate.boundValueOps(activity.getId()+"_activity"+"_ONGOING").expire(endTime.getTime() - (new Date().getTime()), TimeUnit.MILLISECONDS);
            activity.setStatus(MyStatusCode.STATUS_ONGOING);
        }else if(beginTime.compareTo(date) > 0 ){//开始时间大于当前时间，未开始
            redisTemplate.boundValueOps(activity.getId()+"_activity"+"_NOTSTARTED").set(activity.getId());
            redisTemplate.boundValueOps(activity.getId()+"_activity"+"_NOTSTARTED").expire((beginTime.getTime() - new Date().getTime()),TimeUnit.MILLISECONDS);
            activity.setStatus(MyStatusCode.STATUS_NOT_STARTED);

        }
        activity.setCreated(new Date());
        activity.setUpdated(activity.getCreated());

        if(this.activityMapper.insertSelective(activity) != 1){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }
        if(activityRule != null){
            activityRule.setActivityId(activity.getId());
            activityRule.setId(MyNumberUtils.getUUID());
            if(this.activityRuleMapper.insertSelective(activityRule) != 1){
                ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
            }
        }
        if(!CollectionUtils.isEmpty(activityGoodsList)){
            activityGoodsList.forEach(activityGoods -> {
                activityGoods.setActivityId(activity.getId());
                activityGoods.setId(MyNumberUtils.getUUID());
                if(this.activityGoodsMapper.insertSelective(activityGoods) != 1) {
                    ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
                }
            });
        }


    }

    @Transactional
    public void updateActivityInfo(ActivityRequest activityRequest) {
        Activity activity = activityRequest.getActivity();
        ActivityRule activityRule = activityRequest.getActivityRule();
        List<ActivityGoods> activityGoodsList = activityRequest.getActivityGoodsList();
        //数据为空
        //判断需要修改
        if(activity == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        activity.setCreated(this.activityMapper.selectByPrimaryKey(activity).getCreated());
        //activity.setCreated(null);不能这样子设置空值
        activity.setUpdated(new Date());
        if(this.activityMapper.updateByPrimaryKeySelective(activity) != 1){
            ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
        }

        if(activityRule != null){
            if(this.activityRuleMapper.updateByPrimaryKeySelective(activityRule) != 1){
                ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
            }
        }
        //若是商品不为空，则将商品表中该活动的商品都删掉，再加入
        if(!CollectionUtils.isEmpty(activityGoodsList)){
            ActivityGoods ag = new ActivityGoods();
            ag.setActivityId(activity.getId());
            List<ActivityGoods> select = this.activityGoodsMapper.select(ag);
            if(!CollectionUtils.isEmpty(select)){
                select.forEach(s -> {
                    this.activityGoodsMapper.delete(s);
                });
            }
            activityGoodsList.forEach(goods -> {
                goods.setActivityId(activity.getId());
                goods.setId(MyNumberUtils.getUUID());
                this.activityGoodsMapper.insertSelective(goods);
            });
        }
    }

    /**
     * 删除活动
     * @param id
     */
    @Transactional
    public void deleteActivityById(String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                //进行中的活动不允许删除
                Activity activity = this.activityMapper.selectByPrimaryKey(id);
                if(MyStatusCode.STATUS_ONGOING.equals(activity.getStatus())){
                    ExceptionCast.cast(MarketingCode.DELETE_FORBIDDEN);
                }
                //根据活动id找到活动规则和活动商品，删除
                ActivityRule activityRule = new ActivityRule();
                activityRule.setActivityId(id);
                ActivityGoods activityGoods = new ActivityGoods();
                activityGoods.setActivityId(id);
                //activityRule = this.activityRuleMapper.selectOne(activityRule);

                activityRule = this.activityRuleMapper.selectOne(activityRule);
                List<ActivityGoods> activityGoodsList = this.activityGoodsMapper.select(activityGoods);
                this.activityMapper.deleteByPrimaryKey(id);
                if(MyStatusCode.STATUS_NOT_STARTED.equals(activity.getStatus())){
                    redisTemplate.delete(id+"_activity__NOTSTARTED");
                }
                if(StringUtils.isNotBlank(activityRule.getId())){
                    this.activityRuleMapper.delete(activityRule);
                }
                if(!CollectionUtils.isEmpty(activityGoodsList)){
                    activityGoodsList.forEach(activityGoodss -> {
                        this.activityGoodsMapper.delete(activityGoodss);
                    });

                }


            }
        } catch (Exception e) {
            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
            e.printStackTrace();
        }
    }

    /**
     *
     *!!!!!!!!!!!!
     * @param id
     * @return
     */
    public ActivityRequest queryActivityAllById(String id) {
        //为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        ActivityRequest activityRequest = new ActivityRequest();
        //根据主键查找
        Activity activity = this.activityMapper.selectByPrimaryKey(id);
        if(activity == null){
            ExceptionCast.cast(MarketingCode.DATA_NULL);
        }
        this.transferActivity(activity);
        activityRequest.setActivity(activity);
        //
        ActivityRule activityRule = new ActivityRule();
        activityRule.setActivityId(id);
        activityRule = this.activityRuleMapper.selectOne(activityRule);

        if(activityRule!= null){
            activityRequest.setActivityRule(activityRule);
        }


        //
        ActivityGoods activityGoods = new ActivityGoods();
        activityGoods.setActivityId(id);
        List<ActivityGoods> activityGoodsList = this.activityGoodsMapper.select(activityGoods);

        if(!CollectionUtils.isEmpty(activityGoodsList)){
            activityRequest.setActivityGoodsList(activityGoodsList);
        }
        return activityRequest;

    }

    /**
     * 活动改变状态
     * @param status
     * @param id
     */
    @Transactional
    public void changeStatus(String status, String id) {
        Activity activity = new Activity();
        activity.setId(id);
        if("ONGOING".equals(status)){
            activity.setStatus(MyStatusCode.STATUS_FINISHED);
        }else if("NOTSTARTED".equals(status)){
            activity = this.activityMapper.selectByPrimaryKey(id);
            activity.setStatus(MyStatusCode.STATUS_ONGOING);
            redisTemplate.boundValueOps(id+"_activity_ONGOING").set(id);
            redisTemplate.boundValueOps(id+"_activity_ONGOING").expire((activity.getEndTime().getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
        }
        this.activityMapper.updateByPrimaryKeySelective(activity);
    }

    /**
     * 将活动结束
     * @param id
     */
    @Transactional
    public void updateStatusToFinished(String id){
        //数据为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        Activity activity = this.activityMapper.selectByPrimaryKey(id);
        //正在进行中的状态修改为结束，其他状态返回禁止修改操作的消息
        if(MyStatusCode.STATUS_ONGOING.equals(activity.getStatus())){
            activity.setStatus(MyStatusCode.STATUS_FINISHED);
            if(this.activityMapper.updateByPrimaryKeySelective(activity) == 1) {
                redisTemplate.delete(id+"_activity_ONGOING");

            } else {
                ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
            }

        } else {
            ExceptionCast.cast(MarketingCode.UPDATE_FORBIDDEN);
        }


    }
}

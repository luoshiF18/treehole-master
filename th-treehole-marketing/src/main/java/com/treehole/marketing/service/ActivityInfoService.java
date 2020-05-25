package com.treehole.marketing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treehole.framework.domain.marketing.ActivityGoods;
import com.treehole.framework.domain.marketing.ActivityRule;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.marketing.dao.ActivityGoodsMapper;
import com.treehole.marketing.dao.ActivityRuleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wanglu
 */
@Service
public class ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;
    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;
    private static  final ObjectMapper MAPPER = new ObjectMapper();

    public ActivityRule queryActivityRuleById(String id) {
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        ActivityRule activityRule = new ActivityRule();
        activityRule.setActivityId(id);
        activityRule = this.activityRuleMapper.selectOne(activityRule);
        if(activityRule == null){
            ExceptionCast.cast(MarketingCode.DATA_NULL);
        }
        return activityRule;
    }

    public List<ActivityGoods> queryActivityGoodsById(String id) {
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        ActivityGoods activityGoods = new ActivityGoods();
        activityGoods.setActivityId(id);
        List<ActivityGoods> activityGoodsList = this.activityGoodsMapper.select(activityGoods);

        if(CollectionUtils.isEmpty(activityGoodsList)){
            ExceptionCast.cast(MarketingCode.DATA_NULL);
        }
        return activityGoodsList;
    }

   /* @Transactional
    public void saveActivityRule(ActivityRule activityRule){
        activityRule.setId(MyNumberUtils.getUUID());
        this.activityRuleMapper.insertSelective(activityRule);
    }

    @Transactional
    public void saveActivityGoods(ActivityGoods activityGoods){
        activityGoods.setId(MyNumberUtils.getUUID());
        this.activityGoodsMapper.insertSelective(activityGoods);
    }*/

}

package com.treehole.marketing.controller;

import com.treehole.api.marketing.ActivityInfoControllerApi;
import com.treehole.framework.domain.marketing.ActivityGoods;
import com.treehole.framework.domain.marketing.ActivityRule;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.marketing.service.ActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/activity/info")
public class ActivityInfoController implements ActivityInfoControllerApi {

    @Autowired
    private ActivityInfoService activityInfoService;

    @GetMapping("/rule/{id}")
    public ActivityRule queryActivityRuleById(@PathVariable("id") String id) {
        ActivityRule activityRule = this.activityInfoService.queryActivityRuleById(id);
        if(activityRule == null){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return activityRule;
    }

    @GetMapping("/goods/{id}")
    public List<ActivityGoods> queryActivityGoodsById(@PathVariable("id") String id) {
        List<ActivityGoods> activityGoods = this.activityInfoService.queryActivityGoodsById(id);
        if(CollectionUtils.isEmpty(activityGoods)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return activityGoods;
    }
}

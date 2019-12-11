package com.treehole.marketing.api;

import com.treehole.framework.domain.marketing.request.ActivityRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wanglu
 */
public interface ActivitySelectApi {
    /**
     * 根据id查询活动基本信息和规则和商品
     * @param id
     * @return
     */
    @GetMapping("/marketing/activity/all/{id}")
    public ActivityRequest queryActivityAllById(@PathVariable("id") String id);
}

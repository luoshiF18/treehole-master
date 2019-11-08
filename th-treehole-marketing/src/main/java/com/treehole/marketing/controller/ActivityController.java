package com.treehole.marketing.controller;

import com.treehole.api.marketing.ActivityControllerApi;
import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/activity")
public class ActivityController implements ActivityControllerApi {

    @Autowired
    private ActivityService activityService;

    /**
     *
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
    @GetMapping("/page")
    public QueryResponseResult queryActivityByPage(@RequestParam(value = "key", required = false)String key,
                                                   @RequestParam(required = false)Integer status,
                                                   @RequestParam(required = false)Date beginTime,
                                                   @RequestParam(required = false)Date endTime,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "size", defaultValue = "5")Integer size,
                                                   @RequestParam(value = "sortBy", required = false)String sortBy,
                                                   @RequestParam(value = "desc", defaultValue = "false")Boolean desc) {
        QueryResult<Activity> queryResult = this.activityService.queryActivityByPage(key, status, beginTime, endTime, page, size, sortBy, desc);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @GetMapping("/{id}")
    public Activity queryCouponById(@PathVariable String id) {
        Activity activity = this.activityService.queryActivityById(id);
        if(activity == null){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return activity;
    }

    @PostMapping
    public ResponseResult saveActivity(@RequestBody Activity activity) {
        this.activityService.saveActivity(activity);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @PutMapping
    public ResponseResult updateActivityInfo(@RequestBody Activity activity) {
        this.activityService.updateActivityInfo(activity);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteActivityById(@PathVariable String id) {
        this.activityService.deleteActivityById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

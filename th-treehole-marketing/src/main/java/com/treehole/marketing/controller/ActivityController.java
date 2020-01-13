package com.treehole.marketing.controller;

import com.treehole.api.marketing.ActivityControllerApi;
import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.request.ActivityRequest;
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
                                                   @RequestParam(value = "status", required = false)Integer status,
                                                   @RequestParam(value = "beginTime", required = false)Date beginTime,
                                                   @RequestParam(value = "endTime", required = false)Date endTime,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "size", defaultValue = "5")Integer size,
                                                   @RequestParam(value = "sortBy", required = false)String sortBy,
                                                   @RequestParam(value = "desc", defaultValue = "false")Boolean desc,
                                                   @RequestParam(value = "typeId", required = false)String typeId) {
        QueryResult<Activity> queryResult = this.activityService.queryActivityByPage(key, status, beginTime, endTime, page, size, sortBy, desc, typeId);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据id查询活动基本信息和规则和商品！测试
     * @param id
     * @return
     */
    @GetMapping("/all/{id}")
    public ActivityRequest queryActivityAllById(@PathVariable("id") String id){
        ActivityRequest activityRequest = this.activityService.queryActivityAllById(id);
        if(activityRequest == null){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return activityRequest;
    }
    @GetMapping("/{id}")
    public Activity queryActivityById(@PathVariable("id") String id) {
        Activity activity = this.activityService.queryActivityById(id);
        if(activity == null){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return activity;
    }

    @PostMapping
    public ResponseResult saveActivity(@RequestBody ActivityRequest activityRequest) {
        this.activityService.saveActivity(activityRequest);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @PutMapping
    public ResponseResult updateActivityInfo(@RequestBody ActivityRequest activityRequest) {
        this.activityService.updateActivityInfo(activityRequest);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteActivityById(@PathVariable("id") String id) {
        this.activityService.deleteActivityById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping("/status/{id}")
    public ResponseResult updateStatusToFinished(@PathVariable("id") String id) {
        this.activityService.updateStatusToFinished(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

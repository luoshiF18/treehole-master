package com.treehole.marketing.controller;

import com.treehole.api.marketing.InteractiveActivityControllerApi;
import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.InteractiveActivity;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.InteractiveActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/interaction")
public class InteractiveActivityController implements InteractiveActivityControllerApi {
    @Autowired
    private InteractiveActivityService interactiveActivityService;

    @Override
    @GetMapping("/list")
    public QueryResponseResult queryActivityByPage(@RequestParam(value = "key", required = false)String key,
                                                   @RequestParam(value = "status", required = false)Integer status,
                                                   @RequestParam(value = "beginTime", required = false) Date beginTime,
                                                   @RequestParam(value = "endTime", required = false)Date endTime,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "size", defaultValue = "5")Integer size,
                                                   @RequestParam(value = "sortBy", required = false)String sortBy,
                                                   @RequestParam(value = "desc", defaultValue = "false")Boolean desc) {
        QueryResult<InteractiveActivity> queryResult = this.interactiveActivityService.queryActivityByPage(key, status, beginTime, endTime, page, size, sortBy, desc);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    @GetMapping("/info/{id}")
    public InteractiveActivity queryActivityById(@PathVariable("id") String id) {
        InteractiveActivity activity = this.interactiveActivityService.queryActivityById(id);
        if(activity == null){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        return activity;
    }

    @Override
    @PostMapping
    public ResponseResult saveActivity(@RequestBody InteractiveActivity interactiveActivity) {
        this.interactiveActivityService.saveActivity(interactiveActivity);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping
    public ResponseResult updateActivityInfo(@RequestBody InteractiveActivity interactiveActivity) {
        this.interactiveActivityService.updateActivityInfo(interactiveActivity);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseResult deleteActivityById(@PathVariable("id") String id) {
        this.interactiveActivityService.deleteActivityById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @GetMapping("/release/all")
    public QueryResponseResult queryActivityByReleaseTime(@RequestParam("today")@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date today) {
        QueryResult<InteractiveActivity> queryResult = this.interactiveActivityService.queryActivityByReleaseTime(today);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}

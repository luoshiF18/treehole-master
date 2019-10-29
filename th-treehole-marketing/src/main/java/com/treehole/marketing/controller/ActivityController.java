package com.treehole.marketing.controller;

import com.treehole.api.marketing.ActivityControllerApi;
import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.dto.ActivityDTO;
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
@RequestMapping("/activity")
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
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("/page")
    public QueryResponseResult queryActivityByPage(String key, Integer status, Date beginTime, Date endTime, Integer page, Integer rows, String sortBy, Boolean desc) {
        QueryResult<ActivityDTO> queryResult = this.activityService.queryActivityByPage(key, status, beginTime, endTime, page, rows, sortBy, desc);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @GetMapping("/id/{id}")
    public ActivityDTO queryCouponById(String id) {
        Activity activity = this.activityService.queryActivityById(id);
        if(activity == null){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }
        ActivityDTO activityDTO = new ActivityDTO();
        //BeanUtils.copyProperties(activity, activityDTO);
        activityDTO.setId(activity.getId());
        activityDTO.setTitle(activity.getTitle());
        activityDTO.setSubTitle(activity.getSubTitle());
        activityDTO.setBeginTime(activity.getBeginTime());
        activityDTO.setEndTime(activity.getEndTime());
        activityDTO.setDiscount(activity.getDiscount());
        activityDTO.setFixedAmount(activity.getFixedAmount());
        activityDTO.setReduction(activity.getReduction());
        activityDTO.setLimitNum(activity.getLimitNum());
        activityDTO.setWithCoupon(activity.getWithCoupon());
        activityDTO.setCouponId(activity.getCouponId());
        activityDTO.setPoint(activity.getPoint());
        activityDTO.setTypeId(activity.getTypeId());
        activityDTO.setImages(activity.getImages());
        activityDTO.setGoods(activity.getGoods());
        activityDTO.setRule(activity.getRule());
        activityDTO.setStatus(activity.getStatus());
        activityDTO.setDescription(activity.getDescription());
        activityDTO.setCreated(activity.getCreated());
        activityDTO.setUpdated(activity.getUpdated());
        return activityDTO;
    }

    @PostMapping
    public ResponseResult saveActivity(ActivityDTO activityDTO) {
        this.activityService.saveActivity(activityDTO);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @PutMapping
    public ResponseResult updateActivityInfo(ActivityDTO activityDTO) {
        this.activityService.updateActivityInfo(activityDTO);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @DeleteMapping("/id")
    public ResponseResult deleteActivityById(String id) {
        this.activityService.deleteActivityById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

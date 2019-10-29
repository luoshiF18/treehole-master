package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.dto.ActivityDTO;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

/**
 * @author wanglu
 */
@Api(value = "活动管理", description = "对活动进行管理")
public interface ActivityControllerApi {

    @ApiOperation("搜索活动列表")
    public QueryResponseResult queryActivityByPage(String key, Integer status, Date beginTime, Date endTime, Integer page, Integer rows, String sortBy, Boolean desc);

    @ApiOperation("根据活动id查询详细信息")
    public ActivityDTO queryCouponById(String id);

    @ApiOperation("添加活动")
    public ResponseResult saveActivity(ActivityDTO activityDTO);

    @ApiOperation("更改活动信息")
    public ResponseResult updateActivityInfo(ActivityDTO activityDTO);

    @ApiOperation("根据id删除活动")
    public ResponseResult deleteActivityById(String id);

}

package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.request.ActivityRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author wanglu
 */
@Api(value = "活动管理", description = "对活动进行管理")
public interface ActivityControllerApi {

    @ApiOperation("搜索活动列表")
    public QueryResponseResult queryActivityByPage(@RequestParam(value = "key", required = false)String key,
                                                   @RequestParam(value = "status", required = false)Integer status,
                                                   @RequestParam(value = "beginTime", required = false)Date beginTime,
                                                   @RequestParam(value = "endTime", required = false)Date endTime,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "size", defaultValue = "5")Integer size,
                                                   @RequestParam(value = "sortBy", required = false)String sortBy,
                                                   @RequestParam(value = "desc", defaultValue = "false")Boolean desc,
                                                   @RequestParam(value = "typeId", required = false)String typeId);

    @ApiOperation("根据活动id查询详细信息")
    public Activity queryActivityById(@PathVariable("id") String id);

    @ApiOperation("根据活动id查询所有信息")
    public ActivityRequest queryActivityAllById(@PathVariable("id") String id);

    @ApiOperation("添加活动")
    public ResponseResult saveActivity(@RequestBody ActivityRequest activityRequest);

    @ApiOperation("更改活动信息")
    public ResponseResult updateActivityInfo(@RequestBody ActivityRequest activityRequest);

    @ApiOperation("根据id删除活动")
    public ResponseResult deleteActivityById(@PathVariable("id") String id);


    @ApiOperation("修改活动状态为结束")
    public  ResponseResult updateStatusToFinished(@PathVariable("id") String id);
}

package com.treehole.api.marketing;



import com.treehole.framework.domain.marketing.InteractiveActivity;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Api(value = "交互类型活动管理", description = "对交互类型活动进行管理")
public interface InteractiveActivityControllerApi {
    @ApiOperation("搜索活动列表")
    public QueryResponseResult queryActivityByPage(@RequestParam(value = "key", required = false)String key,
                                                   @RequestParam(value = "status", required = false)Integer status,
                                                   @RequestParam(value = "beginTime", required = false) Date beginTime,
                                                   @RequestParam(value = "endTime", required = false)Date endTime,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "size", defaultValue = "5")Integer size,
                                                   @RequestParam(value = "sortBy", required = false)String sortBy,
                                                   @RequestParam(value = "desc", defaultValue = "false")Boolean desc);

    @ApiOperation("根据活动id查询详细信息")
    public InteractiveActivity queryActivityById(@PathVariable("id") String id);

    @ApiOperation("添加活动")
    public ResponseResult saveActivity(@RequestBody InteractiveActivity interactiveActivity);

    @ApiOperation("更改活动信息")
    public ResponseResult updateActivityInfo(@RequestBody InteractiveActivity interactiveActivity);

    @ApiOperation("根据id删除活动")
    public ResponseResult deleteActivityById(@PathVariable("id") String id);


    @ApiOperation("根据发布时间查询活动")
    public QueryResponseResult queryActivityByReleaseTime(@RequestParam("today") String today);

    @ApiOperation("修改互动活动状态为结束")
    public  ResponseResult updateStatusToFinished(@PathVariable("id") String id);
}

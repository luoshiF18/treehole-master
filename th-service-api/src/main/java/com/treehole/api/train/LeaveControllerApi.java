package com.treehole.api.train;
import com.treehole.framework.domain.train.Leave;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="请假管理页面管理接口",description = "请假管理页面管理接口，提供页面的请假、消假")
public interface LeaveControllerApi {

    @ApiOperation("请假")
    public ResponseResult addLeave(Leave leave);

    @ApiOperation("消假")
    public ResponseResult removeLeave(String leaveId);

    @ApiOperation("请假信息统计(请假中人员信息)")
    public QueryResponseResult<Leave> LeaveStatistics(int page,int size ,Leave leave);

    @ApiOperation("请假信息统计(所有人员信息)")
    public QueryResponseResult<Leave> LeaveAllStatistics(int page,int size ,Leave leave);

    @ApiOperation("得到请假人的信息")
    public Leave findInfo(String id);

}

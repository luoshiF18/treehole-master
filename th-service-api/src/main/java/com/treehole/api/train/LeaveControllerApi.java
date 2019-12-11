package com.treehole.api.train;
import com.treehole.framework.domain.train.Leave;
import com.treehole.framework.domain.train.ext.StudentLeaveExamine;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(value="请假管理页面管理接口",description = "请假管理页面管理接口，提供页面的请假、消假")
public interface LeaveControllerApi {

    @ApiOperation("请假")
    public ResponseResult addLeave(Leave leave);

    @ApiOperation("消假")
    public ResponseResult removeLeave(String leaveId);

    @ApiOperation("请假信息统计(请假中人员信息)")
    public QueryResponseResult<Leave> LeaveStatistics(int page,int size ,Leave leave);

    @ApiOperation("请假信息统计(所有人员信息)")
    public QueryResponseResult<Leave> LeaveAllStatistics(int page,int size ,StudentLeaveExamine studentLeaveExamine);

    @ApiOperation("得到请假人的信息")
    public Leave findInfo(String id);

    @ApiOperation("审核通过")
    public ResponseResult examine(String leaveId);

    @ApiOperation("审核不通过")
    public ResponseResult examineNoPass(String leaveId);

    @ApiOperation("查询需要进行审核的信息(学生)")
    public QueryResponseResult<Leave> findLeaveStudentExamine(StudentLeaveExamine studentLeaveExamine , int page, int size);

    @ApiOperation("查询需要进行审核的信息(老师)")
    public QueryResponseResult<Leave> findLeaveTeacherExamine( int page, int size ,Leave leave );

    @ApiOperation("统计个人最后一次请假的记录")
    public QueryResponseResult<Leave> findLastLeaveExamine(String id);

}

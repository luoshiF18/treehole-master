package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.WarnMsg;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 19:12 on 2019/12/13.
 * Version: 1.0
 */
@Api(value = "预警消息通知",description = "预警消息通知管理")
public interface WarnMsgControllerApi {

    @ApiOperation("发送预警消息" )
    public ResponseResult sendMeg(WarnMsg warnMsg);
    @ApiOperation("使用邮件发送预警信息" )
    public ResponseResult sendEamil(WarnMsg warnMsg);
    @ApiOperation("从缓存中得到redis" )
    public List<String> getRedisWarning(String userId);
    @ApiOperation("查询用户邮箱")
    public String emilFormat(WarnMsg warnMsg);
}

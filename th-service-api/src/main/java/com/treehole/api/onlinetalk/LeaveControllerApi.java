package com.treehole.api.onlinetalk;

import com.treehole.framework.domain.onlinetalk.Leave;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author 何文泽
 * @Description:
 * @Date
 */
@Api(value = "留言信息管理", description = "对留言信息进行增删改查")
public interface LeaveControllerApi {
    @ApiOperation("查询所有客服")
    public QueryResponseResult getAllLeave(int page, int size, String name);


    @ApiOperation("创建一条留言信息")
    public ResponseResult insertLeave(@RequestBody @Valid Leave leave);



}

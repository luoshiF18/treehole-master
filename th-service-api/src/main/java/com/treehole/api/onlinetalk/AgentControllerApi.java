package com.treehole.api.onlinetalk;

import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author 何文泽
 * @Description:
 * @Date
 */
@Api(value = "客服信息管理", description = "对客服信息进行增删改查")
public interface AgentControllerApi {
    @ApiOperation("查询所有客服")
    public QueryResponseResult getAllAgent(int page, int size, String agent_name);

    @ApiOperation("通过id查询客服")
    public Agent getAgentById(@PathVariable("id") String id)  ;

    @ApiOperation("创建一条客服信息")
    public ResponseResult insertAgent(@RequestBody @Valid Agent agent);

    @ApiOperation("通过id删除客服")
    public ResponseResult deleteAgentById(@PathVariable("agent_id") String agent_id) ;

    @ApiOperation("更新客服基本信息")
    public ResponseResult updateAgent(@RequestBody @Valid Agent agent);



}

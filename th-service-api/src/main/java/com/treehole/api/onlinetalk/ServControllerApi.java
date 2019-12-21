package com.treehole.api.onlinetalk;

import com.treehole.framework.domain.onlinetalk.Serv;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 何文泽
 * @Description:
 * @Date
 */
@Api(value = "服务信息管理", description = "对服务信息进行增删改查")
public interface ServControllerApi {
    @ApiOperation("查询所有服务信息")
    public QueryResponseResult getAllServ(int page, int size, String agent_name,String user_id);

    @ApiOperation("通过id查询服务信息")
    public Serv getServById(@PathVariable("id") String id)  ;

    @ApiOperation("创建一条服务信息")
    public ResponseResult insertServ(@RequestBody Serv serv);

    @ApiOperation("通过id删除服务信息")
    public ResponseResult deleteServById(@PathVariable("serv_id") String serv_id) ;
    @ApiOperation("通过客服姓名查询服务信息")
    public Serv findServByAgentName(@PathVariable("agent_name") String agent_name);



}

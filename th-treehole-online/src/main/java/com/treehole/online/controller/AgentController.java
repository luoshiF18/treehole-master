package com.treehole.online.controller;

import com.treehole.api.onlinetalk.AgentControllerApi;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("online/agent")
public class AgentController implements AgentControllerApi {
    @Autowired
    private AgentService agentService;


    /**
     * 获取所有客服人员信息
     * @param page
     * @param size
     * @param agent_name
     * @return
     */
    @Override
    @GetMapping ("/getAllAgent")
    public QueryResponseResult getAllAgent( @RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                            @RequestParam(value = "agent_name", defaultValue = "") String agent_name
                                            )  {
        QueryResult queryResult = agentService.findAllAgent(page, size,agent_name);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }


    /**
     * 根据id查询客服
     * @param id
     * @return
     */
    @Override
    @GetMapping("/find/id/{id}")
    public Agent getAgentById(@PathVariable("id") String id) {
         return agentService.getAgentById(id);
    }

    /**
     * 根据id删除客服
     * @param agent_id
     * @return
     */
    @Override
    @DeleteMapping(value ="/delete/id/{agent_id}")
    public ResponseResult deleteAgentById(@PathVariable("agent_id") String agent_id) {
         agentService.deleteAgentById(agent_id);
        return new ResponseResult(CommonCode.SUCCESS);

    }


    /**
     * 新增客服
     * @param agent
     * @return
     */
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertAgent(@RequestBody @Valid Agent agent) {


        agentService.insertAgent(agent);
        return new ResponseResult(CommonCode.SUCCESS);


    }

    /**
     * 修改客服
     * @param agent
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateAgent(@RequestBody @Valid Agent agent){

        agentService.updateAgent(agent);

        return new ResponseResult(CommonCode.SUCCESS);
    }



}

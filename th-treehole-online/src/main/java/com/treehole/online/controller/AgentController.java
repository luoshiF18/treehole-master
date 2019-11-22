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
 * @author shanhuijie
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("agent")
public class AgentController implements AgentControllerApi {
    @Autowired
    private AgentService agentService;



    //http://localhost:40300/user/getAllUsers?page=3

    @Override
    @GetMapping ("/getAllAgent")
    public QueryResponseResult getAllAgent( @RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                            @RequestParam(value = "agent_name", defaultValue = "") String agent_name
                                            )  {
        QueryResult queryResult = agentService.findAllAgent(page, size,agent_name);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }




    @Override
    @GetMapping("/find/id/{id}")
    public Agent getAgentById(@PathVariable("id") String id) {
         return agentService.getAgentById(id);
    }
   /* @GetMapping("/find/{page}/{size}/{agent_name}")
    public QueryResponseResult getAgent(@PathVariable("page") int page,
                                        @PathVariable("size") int size,
                                        @PathVariable("agent_name") String agent_name){
        System.out.println(agent_name);
        QueryResult queryResult = agentService.findAgent(page, size,agent_name);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }*/

    /*@GetMapping("/find/")
    public User getUser*/

    @Override
    @DeleteMapping(value ="/delete/id/{agent_id}")
    public ResponseResult deleteAgentById(@PathVariable("agent_id") String agent_id) {
         agentService.deleteAgentById(agent_id);
         //再判断用户是否存在
         /*if(this.getUserById(user_id) != null){
             ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
         }*/
        return new ResponseResult(CommonCode.SUCCESS);

    }



    @Override
    @PostMapping ("/insert")
    public ResponseResult insertAgent(@RequestBody @Valid Agent agent) {


        agentService.insertAgent(agent);
        return new ResponseResult(CommonCode.SUCCESS);


    }
    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    public ResponseResult updateAgent(@RequestBody @Valid Agent agent){
        //System.out.println("前端传来的+++++++++++++"+user);
        agentService.updateAgent(agent);

        return new ResponseResult(CommonCode.SUCCESS);
    }



}

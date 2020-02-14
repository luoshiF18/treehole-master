package com.treehole.online.controller;

import com.treehole.api.onlinetalk.ServControllerApi;
import com.treehole.framework.domain.onlinetalk.Serv;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.ServService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hwz
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("online/serv")
public class ServController implements ServControllerApi {
    @Autowired
    private ServService servService;


    /**
     * 获取所有客服服务
     * @param page
     * @param size
     * @param agent_name
     * @return
     */
    @Override
    @GetMapping ("/getAllServ")
    public QueryResponseResult getAllServ(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "5") int size,
                                          @RequestParam(value = "agent_name", defaultValue = "")String agent_name,
                                          @RequestParam(value = "user_id", defaultValue = "")String user_id)  {
        QueryResult queryResult = servService.findAllServ(page, size,agent_name,user_id);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }


    /**
     * 根据id查询服务
     * @param id
     * @return
     */
    @Override
    @GetMapping("/find/id/{id}")
    public Serv getServById(@PathVariable("id") String id) {
         return servService.getServById(id);
    }

    /**
     * 根据客服姓名查询服务
     * @param agent_name
     * @return
     */
    @Override
    @GetMapping("/find/{agent_name}")
    public Serv findServByAgentName(@PathVariable("agent_name") String agent_name) {
        return servService.findServByAgentName(agent_name);
    }

    /**
     * 根据id删除服务
     * @param serv_id
     * @return
     */
    @Override
    @DeleteMapping(value ="/delete/id/{serv_id}")
    public ResponseResult deleteServById(@PathVariable("serv_id") String serv_id) {
        servService.deleteServById(serv_id);

        return new ResponseResult(CommonCode.SUCCESS);

    }


    /**
     * 新增服务
     * @param serv
     * @return
     */
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertServ(@RequestBody Serv serv) {


        servService.insertServ(serv);
        return new ResponseResult(CommonCode.SUCCESS);


    }








}

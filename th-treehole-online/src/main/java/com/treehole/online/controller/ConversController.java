package com.treehole.online.controller;

import com.treehole.api.onlinetalk.ConversControllerApi;
import com.treehole.framework.domain.onlinetalk.Convers;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.ConversService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("online/convers")
public class ConversController implements ConversControllerApi {
    @Autowired
    private ConversService conversService;

    /**
     *获取所有会话
     * @param page
     * @param size
     * @param name
     * @return
     */
    @Override
    @GetMapping("/getAllConvers")
    public QueryResponseResult getAllConvers(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "5") int size,
                                              @RequestParam(value = "name", defaultValue = "")String name) {

        QueryResult queryResult =conversService.getAllConvers(page,size,name);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }

    /**
     * 根据id查询会话
     * @param convers_id
     * @return
     */
    @Override
    @GetMapping("/find/convers_id/{convers_id}")
    public Convers getConversById(String convers_id) {
       return conversService.getConversById(convers_id);
    }

    /**
     * 新增会话
     * @param convers
     * @return
     */
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertConvers(@RequestBody @Valid Convers convers) {
        conversService.insertConvers(convers);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除会话
     * @param convers_id
     * @return
     */
    @Override
    @DeleteMapping(value ="/delete/convers_id/{convers_id}")
    public ResponseResult deleteConversById(@PathVariable("convers_id")String convers_id) {
        conversService.deleteConversById(convers_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

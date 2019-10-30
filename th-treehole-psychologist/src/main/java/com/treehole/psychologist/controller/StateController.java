package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.StateControllerApi;
import com.treehole.framework.domain.psychologist.State;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Helay
 * @date 2019/10/29 19:09
 */
@RestController
@RequestMapping("/psychologist/state")
public class StateController implements StateControllerApi {

    @Autowired
    private StateService stateService;

    /**
     * 根据自定义条件分页查询心理咨询师状态信息
     *
     * @param page    当前页码
     * @param size    每页记录数
     * @param id      心理咨询师状态id
     * @param address 心理咨询师详细地址
     * @return
     */
    @Override
    @GetMapping("/find/all")
    public QueryResponseResult findStateByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "address", required = false) String address
    ) {
        QueryResult queryResult = this.stateService.findStateByPage(page, size, id, address);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据id更新心理咨询师状态信息
     *
     * @param state 状态信息
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateState(@RequestBody State state) {
        this.stateService.updateState(state);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据状态id查询状态信息
     *
     * @param id 状态id
     * @return
     */
    @Override
    @GetMapping("/find/{id}")
    public State findStateById(@PathVariable("id") String id) {
        return this.stateService.findStateById(id);
    }

}

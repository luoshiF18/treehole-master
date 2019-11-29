package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.StateControllerApi;
import com.treehole.framework.domain.psychologist.State;
import com.treehole.framework.model.response.QueryResponseResult;
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
     * 分页查询心理咨询师状态信息列表
     *
     * @return
     */
    @Override
    @GetMapping("/find/list")
    public QueryResponseResult findStateList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "free", required = false) String free

    ) {
        return this.stateService.findStateList(page, size, name, price, free);
    }

    /**
     * 根据id查询心理咨询师状态信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Override
    @GetMapping("/find/{id}")
    public State findStateById(@PathVariable("id") String id) {
        return this.stateService.findStateById(id);
    }

    /**
     * 根据id删除心理咨询师状态信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delStateById(@PathVariable("id") String id) {
        return this.stateService.delStateById(id);
    }

    /**
     * 添加心理咨询师简介信息
     *
     * @param state 心理咨询师简介信息
     * @return
     */
    @Override
    @PostMapping("/add")
    public ResponseResult addState(@RequestBody State state) {
        return this.stateService.addState(state);
    }

    /**
     * 根据id更新心理咨询师简介信息
     *
     * @param state 心理咨询师简介信息
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateState(@RequestBody State state) {
        return this.stateService.updateState(state);
    }

    /**
     * 按照id自增查询所有简介信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    @Override
    @GetMapping("get/all")
    public QueryResponseResult getAllStates(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        return this.stateService.getAllStates(page, size);
    }
}

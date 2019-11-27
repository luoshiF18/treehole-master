package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.DetailControllerApi;
import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Helay
 * @date 2019/11/16 9:14
 */
@RestController
@RequestMapping("/psychologist/detail")
public class DetailController implements DetailControllerApi {

    @Autowired
    private DetailService detailService;

    /**
     * 按照id自增分页查询所有咨询师详情信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    @Override
    @GetMapping("/get/list")
    public QueryResponseResult getAllDetails(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        QueryResult queryResult = this.detailService.getAllDetails(page, size);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据咨询师id查询咨询师详情信息
     *
     * @param psychologist_id 咨询师id
     * @return
     */
    @Override
    @GetMapping("/get/{psychologist_id}")
    public Detail getDetailById(@PathVariable("psychologist_id") String psychologist_id) {
        return this.detailService.getDetailById(psychologist_id);
    }

    /**
     * 根据咨询师id删除咨询师详情信息
     *
     * @param psychologist_id 咨询师id
     * @return
     */
    @Override
    @DeleteMapping("/del/{psychologist_id}")
    public ResponseResult delDetailById(@PathVariable("psychologist_id") String psychologist_id) {
        this.detailService.delDetailById(psychologist_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加咨询师详情信息
     *
     * @param detail 详情信息
     * @return
     */
    @Override
    @PostMapping("/add")
    public ResponseResult addDetail(@RequestBody Detail detail) {
        this.detailService.addDetail(detail);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据咨询师id更新其详情信息
     *
     * @param detail 详情信息
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateDetail(@RequestBody Detail detail) {
        this.detailService.updateDetail(detail);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据咨询师姓名查询咨询师详情信息
     *
     * @param psychologist_name 咨询师姓名
     * @return
     */
    @Override
    @GetMapping("/get/all")
    public QueryResponseResult getDetailByName(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "psychologist_name", required = false) String psychologist_name
    ) {
        QueryResult queryResult = this.detailService.getDetailByName(page, size, psychologist_name);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}

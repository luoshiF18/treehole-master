package com.treehole.appointment.controller;

import com.treehole.api.appointment.CltManageControllerApi;
import com.treehole.appointment.service.CltManageService;
import com.treehole.framework.domain.appointment.CltManage;
import com.treehole.framework.domain.appointment.request.QueryCltManageRequest;
import com.treehole.framework.domain.appointment.response.CltManageResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CltManageController
 * @Description:  咨询师预约管理
 * @Author XDD
 * @Date 2019/11/27
 * @Version V1.0
 **/
@RestController
@RequestMapping("/appointment/cltmanage")
public class CltManageController implements CltManageControllerApi {

    @Autowired
    private CltManageService cltManageService;

    /**
    * @Description //新增咨询师预约管理单
    * @Param [cltManage]
    * @return com.treehole.framework.domain.appointment.response.CltManageResult
    **/
    @Override
    @PostMapping("/add")
    public CltManageResult add(@RequestBody CltManage cltManage) {
        return cltManageService.add(cltManage);
    }

    /**
    * @Description //分页查询咨询师预约管理单
    * @Param [page, size, queryCltManageRequest]
    * @return com.treehole.framework.model.response.QueryResponseResult<com.treehole.framework.domain.appointment.CltManage>
    **/
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CltManage> findList(@PathVariable("page") int page,
                                                   @PathVariable("size") int size,
                                                   QueryCltManageRequest queryCltManageRequest) {
        return cltManageService.findList(page,size,queryCltManageRequest);
    }

    /**
    * @Description //删除咨询师预约管理单
    * @Param [id]
    * @return com.treehole.framework.model.response.ResponseResult
    **/
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return cltManageService.delete(id);
    }

    /**
    * @Description //根据id查询咨询师预约管理单信息
    * @Param [id]
    * @return com.treehole.framework.domain.appointment.CltManage
    **/
    @Override
    @GetMapping("/get/{id}")
    public CltManage findById(@PathVariable("id") String id) {
        return cltManageService.findById(id);
    }

    /**
    * @Description //修改咨询师预约管理单
    * @Param [id, cltManage]
    * @return com.treehole.framework.domain.appointment.response.CltManageResult
    **/
    @Override
    @PutMapping("/edit/{id}")
    public CltManageResult update(@PathVariable("id")String id, @RequestBody CltManage cltManage) {
        return cltManageService.update(id,cltManage);
    }
}

package com.treehole.appointment.service;

import com.treehole.framework.domain.appointment.CltManage;
import com.treehole.framework.domain.appointment.request.QueryCltManageRequest;
import com.treehole.framework.domain.appointment.response.CltManageResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;

public interface CltManageService {

    //新增咨询师预约管理
    CltManageResult add(CltManage cltManage);
    //分页查询咨询师预约管理单
    QueryResponseResult<CltManage> findList(int page, int size, QueryCltManageRequest queryCltManageRequest);
    //删除咨询师预约管理单
    ResponseResult delete(String id);
    //根据id查询咨询师预约管理单信息
    CltManage findById(String id);
    //修改咨询师预约管理单
    CltManageResult update(String id, CltManage cltManage);
}

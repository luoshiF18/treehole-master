package com.treehole.api.appointment;

import com.treehole.framework.domain.appointment.CltManage;
import com.treehole.framework.domain.appointment.request.QueryCltManageRequest;
import com.treehole.framework.domain.appointment.response.CltManageResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="咨询师个人预约管理",description = "咨询师个人预约管理管理接口，提供咨询师个人的预约设置的增、删、改、查")
public interface CltManageControllerApi {

    //新增咨询师预约管理单
    @ApiOperation("新增咨询师个人预约管理单")
    public CltManageResult add(CltManage cltManage);

    //分页查询所有
    @ApiOperation("分页查询所有咨询师预约管理单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult<CltManage> findList(int page, int size, QueryCltManageRequest queryCltManageRequest);

    //删除咨询师预约管理单
    @ApiOperation("删除咨询师预约管理单")
    public ResponseResult delete(String id);

    //根据id查询咨询师预约管理单信息
    @ApiOperation("根据id查询咨询师预约管理单信息")
    public CltManage findById(String id);

    //修改咨询师预约管理单
    @ApiOperation("修改咨询师预约管理单")
    public CltManageResult update(String id, CltManage cltManage);

}

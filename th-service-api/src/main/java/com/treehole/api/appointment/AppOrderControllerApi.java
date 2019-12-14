package com.treehole.api.appointment;

import com.treehole.framework.domain.appointment.AppOrder;
import com.treehole.framework.domain.appointment.request.QueryAppOrderRequest;
import com.treehole.framework.domain.appointment.response.AppOrderResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName AppOrderControllerApi
 * @Description: TODO
 * @Author XDD
 * @Date 2019/10/26
 * @Version V1.0
 **/
@Api(value="预约订单管理接口",description = "预约订单管理接口，提供预约单的增、删、改、查")
public interface AppOrderControllerApi {
    //分页查询
    @ApiOperation("分页查询预约订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult<AppOrder> findList(int page, int size, QueryAppOrderRequest queryAppOrderRequest);

    //新增预约单
    @ApiOperation("新增预约订单")
    public AppOrderResult add(AppOrder appOrder);

    //删除预约单
    @ApiOperation("删除预约订单")
    public ResponseResult delete(String id);

    //根据预约单id查询预约单信息
    @ApiOperation("根据预约单id查询预约订单信息")
    public AppOrder findById(String id);

    //修改预约单
    @ApiOperation("修改预约订单")
    public AppOrderResult update(String id,AppOrder appOrder);

}


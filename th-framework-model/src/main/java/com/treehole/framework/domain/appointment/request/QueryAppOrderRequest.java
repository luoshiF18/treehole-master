package com.treehole.framework.domain.appointment.request;

import com.treehole.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接收预约单查询的查询条件
 */
@Data
public class QueryAppOrderRequest extends RequestData {

    //订单id
    @ApiModelProperty("订单id")
    private String id;
    //....
}

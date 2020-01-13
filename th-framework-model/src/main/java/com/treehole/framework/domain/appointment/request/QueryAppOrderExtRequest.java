package com.treehole.framework.domain.appointment.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName QueryAppOrderExtRequest
 * @Description: TODO
 * @Author XDD
 * @Date 2019/12/22 15:45
 **/
@Data
public class QueryAppOrderExtRequest extends QueryAppOrderRequest {
    //订单状态
    @ApiModelProperty("订单状态")
    private Integer appStatus;
}

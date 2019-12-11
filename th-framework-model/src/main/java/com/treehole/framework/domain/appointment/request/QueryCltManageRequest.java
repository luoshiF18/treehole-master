package com.treehole.framework.domain.appointment.request;

import com.treehole.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接收咨询师预约管理单查询的查询条件
 */
@Data
public class QueryCltManageRequest extends RequestData {
    //咨询师id
    @ApiModelProperty("咨询师id")
    private String cltId;

}

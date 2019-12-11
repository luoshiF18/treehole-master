package com.treehole.framework.domain.appointment.response;

import com.treehole.framework.domain.appointment.AppOrder;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;


@Data
public class AppOrderResult extends ResponseResult {

    private AppOrder appOrder;

    public AppOrderResult(ResultCode resultCode, AppOrder appOrder) {
        super(resultCode);
        this.appOrder = appOrder;
    }
}

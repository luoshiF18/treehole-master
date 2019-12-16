package com.treehole.framework.domain.appointment.response;

import com.treehole.framework.domain.appointment.CltManage;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;

@Data
public class CltManageResult extends ResponseResult {

    private CltManage cltManage;

    public CltManageResult(ResultCode resultCode, CltManage cltManage) {
        super(resultCode);
        this.cltManage = cltManage;
    }
}

package com.treehole.framework.domain.member.result;

import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;

/**
 * @author wanglu
 */
@Data
public class BeanResponse<T> extends ResponseResult {

    private T bean;

    public BeanResponse(ResultCode resultCode, T bean) {
        super(resultCode);
        this.bean = bean;
    }
}

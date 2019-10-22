package com.treehole.framework.domain.member.result;

import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * @author wanglu
 */
@Data
public class ListResponse<T> extends ResponseResult {
    private List<T> beans;

    public ListResponse(ResultCode resultCode, List<T> beans) {
        super(resultCode);
        this.beans = beans;
    }
}

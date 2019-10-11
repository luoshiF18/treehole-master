package com.treehole.framework.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 10:44
 * @description: 带有数据的响应类型
 */
@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode,QueryResult queryResult){
        super(resultCode);
        this.queryResult = queryResult;
    }

}

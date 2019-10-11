package com.treehole.framework.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 10:46
 * @description:
 */
@Data
@ToString
public class QueryResult<T> {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;
}

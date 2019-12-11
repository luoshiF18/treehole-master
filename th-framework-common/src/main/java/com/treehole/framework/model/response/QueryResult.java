package com.treehole.framework.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 10:46
 * @description:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueryResult<T> implements Serializable {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;

}

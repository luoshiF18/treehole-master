package com.treehole.framework.domain.evaluation.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Qbl
 * Created by 15:55 on 2019/11/19.
 * Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class PieData {

    private Integer value;
    private String name;
}

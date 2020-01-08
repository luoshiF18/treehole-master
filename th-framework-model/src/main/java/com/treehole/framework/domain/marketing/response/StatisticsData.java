package com.treehole.framework.domain.marketing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wanglu
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsData {
    private Integer value;
    private String name;

}

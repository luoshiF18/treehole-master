package com.treehole.framework.domain.evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaleDetailVO2 {
    private String id;
    private String scaleName;
    private String shortName;
    private String topicDescription;
    private String topicSuggest;
    private String topicBackground;
    private String guide;
    private String scaleFunction;
    private String createTime;
    private String createUserId;
    private String updateTime;
    private String updateUserId;
    private String images;
    private String type;
    private Integer scaleType;
    private String remark;
}

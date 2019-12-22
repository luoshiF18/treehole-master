package com.treehole.framework.domain.evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private String createUserName;
    private String updateTime;
    private String updateUserId;
    private String updateUserName;
    private String images;
    private Integer status;
    private String statusName;
    private String typeId;
    private String type;
    private Integer scaleType;
    private String scaleTypeName;
    private BigDecimal price;
    private Integer questionTotal;
    private String scoreMethodId;
    private String scoreMethodName;
    private String remark;

}

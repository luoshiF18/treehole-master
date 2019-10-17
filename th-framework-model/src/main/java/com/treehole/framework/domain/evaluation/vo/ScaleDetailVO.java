package com.treehole.framework.domain.evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @auther: Yan Hao
 * @date: 2019/10/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaleDetailVO {
    private String id;
    private String scaleName;
    private String shortName;
    private String topicDescription;
    private String topicSuggest;
    private String topicBackground;
    private String guide;
    private String scaleFunction;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String createTime;
    private String images;
    private String type;
    private Integer scaleType;
}

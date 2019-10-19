package com.treehole.framework.domain.evaluation.vo;

import lombok.Data;

/**
 * @auther: Yan Hao
 * @date: 2019/10/16
 */
@Data
public class ResultVO {

    private String userName;
    private String scaleName;
    private String descriptionInfo;
    private Float score;
    private String warningInfo;
    private Integer warningLevel;
}

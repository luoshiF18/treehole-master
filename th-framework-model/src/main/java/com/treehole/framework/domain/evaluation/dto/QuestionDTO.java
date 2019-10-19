package com.treehole.framework.domain.evaluation.dto;

import com.treehole.framework.domain.evaluation.Option;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@Data
public class QuestionDTO {

    private String scaleId;
    private Integer sort;
    private String question;
    private Date createTime;
    private String createUserId;
    private Date updateTime;
    private String updateUserId;
    private String remark;
    //    增加选项
    private List<Option> options;
}


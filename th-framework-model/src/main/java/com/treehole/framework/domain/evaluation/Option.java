package com.treehole.framework.domain.evaluation;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 量表选项
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "test_option")
public class Option implements Serializable {

    @Id
    private String id;
    private String questionId;
    private String answer;
    private Integer score;
    private Integer skip;
    private Integer skipId;
    private Integer state;
    private String remark;
}

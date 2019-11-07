package com.treehole.framework.domain.evaluation;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 量表选项
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "test_option")
@NameStyle(Style.normal)
public class Option implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String questionId;
    private Integer sort;
    private String answer;
    private Float score;
    private Integer skip;
    private String skipId;
    private String scaleId;
    @Transient
    private Integer skipQuestionSort;// 问题序号，这个不会作为数据库字段,用于修改问题展示用questionVO3
    @Transient
    private String skipName;
}

package com.treehole.framework.domain.evaluation;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

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
@NameStyle(Style.normal)
public class Option implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String questionId;
    private Integer sort;
    private String answer;
    private Integer score;
    private Integer skip;
    private Integer skipId;
    private String scaleId;
}

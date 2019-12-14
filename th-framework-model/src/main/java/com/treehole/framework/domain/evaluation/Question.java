package com.treehole.framework.domain.evaluation;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 量表问题
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "test_question")
@NameStyle(Style.normal)
public class Question implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String scaleId;
    private Integer sort;
    private String question;
/*    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;
    private String createUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;
    private String updateUserId;
    private String remark;*/
}

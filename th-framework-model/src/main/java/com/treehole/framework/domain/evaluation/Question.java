package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 量表问题
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "test_question")
public class Question implements Serializable {

    @Id
    private String id;
    private String scaleId;
    private Integer sort;
    private String question;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;
    private String createUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;
    private String updateUserId;
    private Integer state;
    private String remark;
}

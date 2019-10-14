package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 量表描述
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "test_description")
public class Description implements Serializable {

    @Id
    private String id;
    private String scaleId;
    private String score1;
    private String score2;
    private String description;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;
    private String createUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;
    private String updateUserId;
    private Integer warningLevel;

}

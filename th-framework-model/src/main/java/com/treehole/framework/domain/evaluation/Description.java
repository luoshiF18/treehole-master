package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

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
@NameStyle(Style.normal)
public class Description implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String scaleId;
    private Integer score1;
    private Integer score2;
    private String description;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;
    private String createUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;
    private String updateUserId;
    private Integer warningLevel;

}

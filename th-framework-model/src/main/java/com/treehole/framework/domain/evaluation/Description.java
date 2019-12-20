package com.treehole.framework.domain.evaluation;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
    private Float score1;
    private Float score2;
    private String description;
/*    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    private String createUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    private String updateUserId;*/
    private Integer warningLevel;
    private String warningMessage;
/*    //    查出姓名
    @Transient
    private String createUserName;
    @Transient
    private String updateUserName;
    //    时间展示
    @Transient
    private String upTime;
    @Transient
    private String crTime;*/
}

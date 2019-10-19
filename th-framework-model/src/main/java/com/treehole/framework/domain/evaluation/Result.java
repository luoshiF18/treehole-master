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
 * 测评结果
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "result")
@NameStyle(Style.normal)
public class Result implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String userId;
    private String scaleName;
    private String description;
    private Float score;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    private String userWarningInfo;
    private String updateUserId;
    private Date updateTime;
    private String warningInfo;
    private Integer resultType;
    private String remark;


}

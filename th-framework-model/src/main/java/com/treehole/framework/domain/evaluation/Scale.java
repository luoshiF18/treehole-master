package com.treehole.framework.domain.evaluation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 量表信息
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "test_scale")
@NameStyle(Style.normal)
public class Scale implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String scaleName;
    private String shortName;
    private String letter;
    private String topicDescription;
    private String topicSuggest;
    private String topicBackground;
    private String guide;
    private String scaleFunction;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private String createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    private String updateUserId;
    private String images;
    private String typeId;
    private Integer scaleType;
    private BigDecimal price;
    private String remark;

    @Transient
    private String typeName;
    @Transient
    private String scaleTypeName;
    @Transient
    private String  statusName;
}

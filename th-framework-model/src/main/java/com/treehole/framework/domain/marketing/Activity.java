package com.treehole.framework.domain.marketing;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_activity")
public class Activity implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String title;
    private String subTitle;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String typeId;
    @Transient
    private String typeName;
    private String images;
    private String rule;
    private Integer status;
    @Transient
    private String statusName;
    private String description;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date created;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updated;


}

package com.treehole.framework.domain.psychologist;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 心理咨询师基本信息
 */
@Data
@ToString
@Table(name = "psychologist_info")
@NameStyle(Style.normal)
public class Info implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;

    private String name;

    private Integer age;

    private Integer gender;

    private String region;

    private String grade;

}

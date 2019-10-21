package com.treehole.framework.domain.psychologist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Table(name = "psychologist_info")
@NameStyle(Style.normal)
public class info {

    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty("心理咨询师id")
    private String id;

    @ApiModelProperty("心理咨询师姓名")
    private String name;

    @ApiModelProperty("心理咨询师年龄")
    private Integer age;

    @ApiModelProperty("心理咨询师性别 0：男 1：女")
    private Integer gender;

    @ApiModelProperty("心理咨询师所在地区")
    private String region;

    @ApiModelProperty("心理咨询师专业资质（等级）")
    private String grade;

}

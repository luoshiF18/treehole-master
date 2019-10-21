package com.treehole.framework.domain.psychologist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 心理咨询师状态信息
 */
@Data
@ToString
@Table(name = "psychologist_state")
@NameStyle(Style.normal)
public class state {

    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty("心理咨询师状态id")
    private String id;

    @ApiModelProperty("心理咨询师自我介绍")
    private String introduction;

    @ApiModelProperty("心理咨询师擅长领域")
    private String proficiency;

    @ApiModelProperty("心理咨询师手机号码")
    private String phone;

    @ApiModelProperty("心理咨询师qq")
    private String qq;

    @ApiModelProperty("心理咨询师微信")
    private String weChat;

    @ApiModelProperty("心理咨询师详细地址")
    private String address;

    @ApiModelProperty("心理咨询师开设课程")
    private String course;

    @ApiModelProperty("心理咨询师是否空闲 0：是  1：否")
    private Integer state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty("心理咨询师创建时间")
    private Date createTime;
}

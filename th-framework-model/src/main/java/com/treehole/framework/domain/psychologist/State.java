package com.treehole.framework.domain.psychologist;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 心理咨询师状态信息
 */
@Data
@ToString
@Table(name = "psychologist_state")
@NameStyle(Style.normal)
public class State implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;

    private String introduction;

    private String proficiency;

    private String phone;

    private String qq;

    private String weChat;

    private String address;

    private String course;

    private Integer state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
}

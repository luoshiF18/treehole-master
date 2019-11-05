package com.treehole.framework.domain.psychologist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 心理咨询师基本信息实体类
 *
 * @author Helay
 * @date 2019/10/25 8:55
 */
@Data
@Table(name = "profile")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class Profile implements Serializable {

    //心理咨询师id
    @Id
    private String id;

    //心理咨询师姓名
    private String name;

    //心理咨询师性别
    private String sex;

    //心理咨询师年龄
    private String age;

    //心理咨询师所在地区
    private String region;

    //心理咨询师资质
    private String qualification;

    //心理咨询师自我介绍
    private String introduction;

    //心理咨询师擅长领域
    private String proficiency;

    //心理咨询师工作室
    private String studio;

    //心理咨询师手机
    private String phone;

    //心理咨询师创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    //心理咨询师信息更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

}

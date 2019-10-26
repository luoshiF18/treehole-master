package com.treehole.framework.domain.psychologist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 心理咨询师特有信息实体类
 *
 * @author Helay
 * @date 2019/10/25 8:55
 */
@Data
@Table(name = "psychologist")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class Psychologist {

    //心理咨询师id
    @Id
    private String id;

    //心理咨询师自我介绍
    private String introduction;

    //心理咨询师擅长领域
    private String proficiency;

    //心理咨询师资质
    private String qualification;

    //心理咨询师工作室
    private String studio;

    //心理咨询师是否空闲 0：是  1：否
    private Integer state;

    //心理咨询师咨询价格
    private BigDecimal price;

    //心理咨询师创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    //心理咨询师信息更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

}

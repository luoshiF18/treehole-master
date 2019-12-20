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
 * @author Helay
 * @date 2019/10/29 18:40
 */
@Data
@Table(name = "state")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class State implements Serializable {

    //心理咨询师状态id
    @Id
    private String id;

    //心理咨询师状姓名
    private String name;

    //心理咨询师是否空闲
    private String free;

    //心理咨询师咨询价格
    private String price;

    //心理咨询师所执证书
    private String certificate;

    //心理咨询师微信
    private String we_chat;

    //心理咨询师qq
    private String qq;

    //心理咨询师所在详细地址
    private String address;

    //心理咨询师信息更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date update_time;

    //心理咨询师信息创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time;

}

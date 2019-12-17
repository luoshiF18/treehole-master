package com.document.manage.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lee
 * @Date: 2019/10/26
 * @Description: com.lee.springboot.pojo
 * @version: 1.0
 */
@Data
@Entity
public class Document implements Serializable {
    @Id
    private String id;
    private String name;
    private String typeId;
    private String url;
    private String folderId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String uploadId;
    private String docDescribe;
}

package com.document.manage.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lee
 * @Date: 2019/11/16
 * @Description: com.lee.springboot.pojo
 * @version: 1.0
 */
@Data
@Entity
public class Logs implements Serializable {
    @Id
    private String id;//id
    private Integer action;//进行的操作（0：物理删除操作；1：逻辑删除操作；2：还原回收站中操作；3：修改名称操作；4：下载操作；）
    private String docId;//操作的文件或文件夹的id
    private Integer type;//操作的类型（0：操作文件夹；1：操作的是文件）
    private String userId;//操作用户的id
    private Date date;//进行相关操作的时间
}

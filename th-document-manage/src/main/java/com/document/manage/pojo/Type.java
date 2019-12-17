package com.document.manage.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.pojo
 * @version: 1.0
 */
@Data
@Entity
public class Type implements Serializable {
    @Id
    private String id;
    private String type;
}

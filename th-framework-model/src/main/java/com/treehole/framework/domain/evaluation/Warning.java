package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: Qbl
 * Created by 11:35 on 2019/10/14.
 * Version: 1.0
 */
@Data
@Table(name = "warning")
public class Warning {

    @Id
    private String id;
    private String userId;
    private  String ScaleId;
    private  Integer status;
    private  Integer grade;
    private String wMessage;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    }

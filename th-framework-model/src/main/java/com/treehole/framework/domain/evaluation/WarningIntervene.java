package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Qbl
 * Created by 17:29 on 2019/11/23.
 * Version: 1.0
 */
@Data
@Table(name = "warning_intervene")
public class WarningIntervene implements Serializable{

    @Id
    private String id;
    private String way;   //干预方式
    private String userName;  //干预的对象
    private String warningId; //关联预警表
    private String context; //进行的内容
    private String reason;  //干预的原因
    private String effect;  //干预后有什么影响
    private String advisoryName; //干预人员
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date recordTime;  //干预进行的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;//记录表生成的时间
    private Integer status; //干预后的状态是良好 或不好
}

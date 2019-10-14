package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 测评结果
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "result")
public class Result implements Serializable {

    @Id
    private String id;
    private String userId;
    private String scaleId;
    private String descriptionId;
    private Integer score;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;
    private String warningId;
    private String remark;


}

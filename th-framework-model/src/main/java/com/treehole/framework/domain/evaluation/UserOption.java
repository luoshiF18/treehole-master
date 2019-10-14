package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户选项id
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "user_option")
public class UserOption implements Serializable {

    @Id
    private String id;
    private String scaleId;
    private String userId;
    private String questionId;
    private String optionId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date time;

}

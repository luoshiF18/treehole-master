package com.treehole.framework.domain.evaluation.request;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: Qbl
 * Created by 11:21 on 2019/10/30.
 * Version: 1.0
 */
@Data
@ToString
public class WarnRequest {
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    private String scaleName;
    private String userNickName;
    private Integer warningLevel;
    private String userId;
}

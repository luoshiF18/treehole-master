package com.treehole.framework.domain.evaluation.request;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 11:21 on 2019/10/30.
 * Version: 1.0
 */
@Data
@ToString
public class WarnRequest {

    private String startTime;
    private String endTime;
    private String scaleName;
    private String userNickName;
    private Integer warningLevel;
    private String userId;
    private String companyId;
    private List<String> userIds;
}

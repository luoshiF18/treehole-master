package com.treehole.framework.domain.evaluation.request;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Qbl
 * Created by 9:14 on 2019/11/25.
 * Version: 1.0
 */
@Data
@ToString
public class InterveneRequest {

    private String startTime;
    private String endTime;
    private String userNickName;
    private String advisoryName;
    private String organizationName;
}

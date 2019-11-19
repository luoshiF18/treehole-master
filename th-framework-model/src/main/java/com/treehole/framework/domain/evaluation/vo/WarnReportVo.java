package com.treehole.framework.domain.evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Qbl
 * Created by 10:23 on 2019/10/29.
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarnReportVo extends WarningVo {

    private String userPic;    //用户头像
    private String userEmail;  //用户邮箱
    private String userRegion; //用户所在地

    private String scaleType;     //量表类型
    private String scaleFunction; //量表功能
    private String topicDescription; //量表描述

}

package com.treehole.framework.domain.archives.ext;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/21 17:22
 * @description: 个体报告详细内容
 */
@Data
public class ResultExt extends ResultBase{

    //用户昵称
    private String user_nickname;
    //用户性别
    private Integer gender; //用户性别 0：男 1：女
    //用户出生日期
    private Date user_birth; //用户出生年月

    //量表的基本描述信息
    private String topicBackground;  //量表所测主题描述

    private String topicSuggest;   //主题问题的建议

    //原始答卷
    //问题
    //答案

    private List<ResuAnswer> questionAndOption; //整体原始答卷

    private String heal_level;    //健康等级

}

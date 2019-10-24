package com.treehole.framework.domain.archives.ext;

import lombok.Data;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/23 17:38
 * @description: resultBase
 */
@Data
public class ResultBase {

    //resultId
    private String id;
    //userId
    private String userId;
    //用户名
    private String user_name;
    //量表名
    private String scaleName;
    //描述信息
    private String description;
    //量表分数
    private Float score;
    //result创建时间
    private String createTime;
    //预警信息
    private String warningInfo;
    //咨询师预警
    private String userWarningInfo;

}

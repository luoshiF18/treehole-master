package com.treehole.framework.domain.archives.resquest;

import lombok.Data;
import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 9:21
 * @description: 个体报告请求条件
 */
@Data
@ToString
public class ResultListRequest {

    //需要时添加
    private String userId;  //用户id

    private String scaleName; //量表名称

    private String user_nickname;  //用户昵称
}

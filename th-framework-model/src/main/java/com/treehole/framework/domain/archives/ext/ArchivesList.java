package com.treehole.framework.domain.archives.ext;

import lombok.Data;

import java.util.Date;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/23 10:44
 * @description: 个人档案列表单项内容
 */
@Data
public class ArchivesList {

    private String userId;  //用户id
    private String userName;
    //用户昵称
    private String user_nickname;
    //量表名称
    private String scaleName;
    //作答次数
    private Integer answerCount;
    //最新作答时间
    private Date lateFinishDate;
    //量表维度名称
    //...
}

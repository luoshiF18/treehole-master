package com.treehole.framework.domain.archives.ext;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 20:40
 * @description: 个人档案详细内容
 */
@Data
public class ArchivesExt{

    //量表名
    private String scaleName;
    //用户昵称
    private String user_nickname;
    //userId
    private String userId;
    //用户名
    private String user_name;
    //用户性别
    private Integer gender; //用户性别 0：男 1：女
    //用户出生日期
    private Date user_birth; //用户出生年月
    //用户手机号
    private String user_phone; //用户电话
    //量表完成次数
    private Integer scaleCount;


    //量表的基本描述信息
    private String topicBackground;  //量表所测主题描述

    //历次作答记录
    List<ResultTiny> resultTinies;

}

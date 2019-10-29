package com.treehole.framework.domain.archives.ext;

import lombok.Data;

import java.util.Date;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/24 14:58
 * @description: Result缩小版
 */
@Data
public class ResultTiny {

    //时间
    private Date createTime;
    //得分
    private Float score;
    //预警信息
    String warningInfo;

}

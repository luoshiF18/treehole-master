package com.treehole.framework.domain.archives.ext;

import lombok.Data;

import java.util.Date;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/23 10:19
 * @description: 个体报告列表单项内容
 */
@Data
public class ResultList extends ArchivesList{

    //个体报告id
    private String id;

    //用户角色
    private String role_name;//角色名称

    private Date create_date; //创建时间

    //预警等级
    //...

}

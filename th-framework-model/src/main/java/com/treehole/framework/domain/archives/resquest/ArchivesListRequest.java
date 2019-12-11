package com.treehole.framework.domain.archives.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 9:21
 * @description: 个体档案的请求条件
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArchivesListRequest {

    //用户id
    String userId;
    //量表名称
    String scaleName;
}

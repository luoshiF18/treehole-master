package com.treehole.framework.domain.member.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Shan HuiJie
 * @Description: 暴露给其他人，用来查询等级信息
 * @Date 2019.11.9 13:55
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GradeListRequest {
    //等级ID
    String grade_id;

    //等级昵称
    String grade_name;

    //等级级别
    Integer rank;
}

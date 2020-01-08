package com.treehole.framework.domain.member.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Shan HuiJie
 * @Description: 暴露给其他人，用来获取user信息
 * @Date 2019.10.29 17:27
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleListRequest {
    //ID
    String id;

    //名称
    String name;

}

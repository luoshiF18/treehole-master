package com.treehole.framework.domain.member.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Shan HuiJie
 * @Description: 暴露给其他人，用来获取Card信息
 * @Date 2019.11.11 10:24
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardListRequest {
    //用户ID
    String user_id;

    //会员卡id
    String card_id;

    //用户手机号
    String user_phone;
}

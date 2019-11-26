package com.treehole.framework.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shanhuijie
 * @Description: 付费等级信息
 * @Date
 */
@Data
@Table(name = "member_paygrade")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class PayGrade implements Serializable {
    private String paygrade_id; //等级id
    private String paygrade_name; //等级名称
    private BigDecimal card_price;  //售卡金额
    private Integer card_legality;  //有效期 （单位：天）
    private String card_desc;  //会员权益描述
    private BigDecimal initial_money;  //初始储值
    private Integer initial_points;  //初始积分
    private Double discount;  //付费会员折扣
    private Integer rank;


}

package com.treehole.framework.domain.member.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员卡表信息
 * @Date
 */
@Data
@Table(name = "member_cards")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class CardsVo implements Serializable {
    @Id //声明主键字段
    private String card_id;  //会员卡id
    private String user_id;  //用户id
    //private String user_nickname;  //用户昵称

    private String paygrade;  //付费等级名称
    private String freegrade;  //非付费等级名称
    private BigDecimal consum_all;  //总消费金额
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date paygrade_start;  //付费会员办卡时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date paygrade_end;  //付费会员到期日
    private Integer points_now;//现有积分
    private Integer points_sum;  //累计积分



}

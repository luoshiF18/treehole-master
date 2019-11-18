package com.treehole.framework.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author shanhuijie
 * @Description: 非付费会员等级信息
 * @Date
 */
@Data
@Table(name = "member_freegrade")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class FreeGrade implements Serializable {
    private String freegrade_id; //等级id
    private String freegrade_name; // 等级名称
    private Integer points_judge; //判断积分数
    private Double consum_judge; //判定消费金额


}

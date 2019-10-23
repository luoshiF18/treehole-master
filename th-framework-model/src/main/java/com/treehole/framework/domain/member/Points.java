package com.treehole.framework.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 积分信息
 * @Date
 */
@Data
@Table(name = "member_points")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class Points implements Serializable {
    private String points_id;  //记录积分id
    private String user_id;  //用户id
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date points_time;  //记录时间
    private Integer points_before;//积分计算之前数量
    private Integer points_num;  //记录本次积分数
    private Integer points_later;  //积分计算之后数量
    private Integer points_cumulate;  //累计积分
    private Integer points_clear;  //消去积分


}

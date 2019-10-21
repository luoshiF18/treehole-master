package com.treehole.framework.domain.member;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author shanhuijie
 * @Description: 非付费会员等级信息
 * @Date
 */
@Data
@Table(name = "member_freegrade")
public class FreeGrade implements Serializable {
    private String freegrade_id; //等级id
    private String freegrade_name; // 等级名称
    private Integer points_judge; //判断积分数
    private Double consum_judge; //判定消费金额

    public FreeGrade() {
    }

    public String getFreegrade_id() {
        return freegrade_id;
    }

    public void setFreegrade_id(String freegrade_id) {
        this.freegrade_id = freegrade_id;
    }

    public String getFreegrade_name() {
        return freegrade_name;
    }

    public void setFreegrade_name(String freegrade_name) {
        this.freegrade_name = freegrade_name;
    }

    public Integer getPoints_judge() {
        return points_judge;
    }

    public void setPoints_judge(Integer points_judge) {
        this.points_judge = points_judge;
    }

    public Double getConsum_judge() {
        return consum_judge;
    }

    public void setConsum_judge(Double consum_judge) {
        this.consum_judge = consum_judge;
    }
}

package com.treehole.framework.domain.member;

import lombok.Data;

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
public class Points implements Serializable {
    private String points_id;  //记录积分id
    private String user_id;  //用户id
    private Date points_time;  //记录时间
    private Integer points_num;  //记录本次积分数
    private Integer points_later;  //会员计算之后积分
    private Integer points_cumulate;  //累计积分
    private Integer points_clear;  //消去积分

    public Points() {
    }

    public Points(String points_id, String user_id, Date points_time, Integer points_num, Integer points_later, Integer points_cumulate, Integer points_clear) {
        this.points_id = points_id;
        this.user_id = user_id;
        this.points_time = points_time;
        this.points_num = points_num;
        this.points_later = points_later;
        this.points_cumulate = points_cumulate;
        this.points_clear = points_clear;
    }

    @Override
    public String toString() {
        return "Points{" +
                "points_id='" + points_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", points_time=" + points_time +
                ", points_num=" + points_num +
                ", points_later=" + points_later +
                ", points_cumulate=" + points_cumulate +
                ", points_clear=" + points_clear +
                '}';
    }

    public String getPoints_id() {
        return points_id;
    }

    public void setPoints_id(String points_id) {
        this.points_id = points_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getPoints_time() {
        return points_time;
    }

    public void setPoints_time(Date points_time) {
        this.points_time = points_time;
    }

    public Integer getPoints_num() {
        return points_num;
    }

    public void setPoints_num(Integer points_num) {
        this.points_num = points_num;
    }

    public Integer getPoints_later() {
        return points_later;
    }

    public void setPoints_later(Integer points_later) {
        this.points_later = points_later;
    }

    public Integer getPoints_cumulate() {
        return points_cumulate;
    }

    public void setPoints_cumulate(Integer points_cumulate) {
        this.points_cumulate = points_cumulate;
    }

    public Integer getPoints_clear() {
        return points_clear;
    }

    public void setPoints_clear(Integer points_clear) {
        this.points_clear = points_clear;
    }
}

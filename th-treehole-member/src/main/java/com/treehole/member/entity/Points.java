package com.treehole.member.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 积分信息
 * @Date
 */
public class Points implements Serializable {
    private String points_id;  //记录积分id
    private String user_id;  //用户id
    private Date points_time;  //记录时间
    private int points_num;  //记录本次积分数
    private int points_now;  //会员现有积分
    private int points_cumulate;  //累计积分
    private int points_clear;  //消去积分

    public Points() {
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

    public int getPoints_num() {
        return points_num;
    }

    public void setPoints_num(int points_num) {
        this.points_num = points_num;
    }

    public int getPoints_now() {
        return points_now;
    }

    public void setPoints_now(int points_now) {
        this.points_now = points_now;
    }

    public int getPoints_cumulate() {
        return points_cumulate;
    }

    public void setPoints_cumulate(int points_cumulate) {
        this.points_cumulate = points_cumulate;
    }

    public int getPoints_clear() {
        return points_clear;
    }

    public void setPoints_clear(int points_clear) {
        this.points_clear = points_clear;
    }
}

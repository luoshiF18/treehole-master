package com.treehole.member.entity;

import java.io.Serializable;

/**
 * @author shanhuijie
 * @Description: 付费等级信息
 * @Date
 */
public class PayGrade implements Serializable {
    private String paygrade_id; //等级id
    private String paygrade_name; //等级名称
    private double card_price;  //售卡金额
    private int card_legality;  //有效期 （单位：天）
    private String card_desc;  //会员权益描述
    private int initial_money;  //初始储值
    private int initial_points;  //初始积分
    private double discount;  //付费会员折扣

    public PayGrade() {
    }

    public String getPaygrade_id() {
        return paygrade_id;
    }

    public void setPaygrade_id(String paygrade_id) {
        this.paygrade_id = paygrade_id;
    }

    public String getPaygrade_name() {
        return paygrade_name;
    }

    public void setPaygrade_name(String paygrade_name) {
        this.paygrade_name = paygrade_name;
    }

    public double getCard_price() {
        return card_price;
    }

    public void setCard_price(double card_price) {
        this.card_price = card_price;
    }

    public int getCard_legality() {
        return card_legality;
    }

    public void setCard_legality(int card_legality) {
        this.card_legality = card_legality;
    }

    public String getCard_desc() {
        return card_desc;
    }

    public void setCard_desc(String card_desc) {
        this.card_desc = card_desc;
    }

    public int getInitial_money() {
        return initial_money;
    }

    public void setInitial_money(int initial_money) {
        this.initial_money = initial_money;
    }

    public int getInitial_points() {
        return initial_points;
    }

    public void setInitial_points(int initial_points) {
        this.initial_points = initial_points;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

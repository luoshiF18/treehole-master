package com.treehole.member.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员卡表信息
 * @Date
 */
public class Cards implements Serializable {
    private String card_id;  //会员卡id
    private String user_id;  //用户id
    private String paygrade_id;  //付费等级id
    private String freegrade_id;  //非付费等级id
    private Double consum_all;  //总消费金额
    private Date paygrade_start;  //付费会员办卡时间
    private Date paygrade_end;  //付费会员到期日

    public Cards() {
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPaygrade_id() {
        return paygrade_id;
    }

    public void setPaygrade_id(String paygrade_id) {
        this.paygrade_id = paygrade_id;
    }

    public String getFreegrade_id() {
        return freegrade_id;
    }

    public void setFreegrade_id(String freegrade_id) {
        this.freegrade_id = freegrade_id;
    }

    public Double getConsum_all() {
        return consum_all;
    }

    public void setConsum_all(Double consum_all) {
        this.consum_all = consum_all;
    }

    public Date getPaygrade_start() {
        return paygrade_start;
    }

    public void setPaygrade_start(Date paygrade_start) {
        this.paygrade_start = paygrade_start;
    }

    public Date getPaygrade_end() {
        return paygrade_end;
    }

    public void setPaygrade_end(Date paygrade_end) {
        this.paygrade_end = paygrade_end;
    }
}

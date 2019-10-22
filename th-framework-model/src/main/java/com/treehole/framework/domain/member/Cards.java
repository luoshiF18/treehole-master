package com.treehole.framework.domain.member;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员卡表信息
 * @Date
 */
@Data
@Table(name = "member_cards")

public class Cards implements Serializable {
    @Id //声明主键字段
    private String card_id;  //会员卡id
    private String user_id;  //用户id
    private String paygrade_id;  //付费等级id
    private String freegrade_id;  //非付费等级id
    private Double consum_all;  //总消费金额
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date paygrade_start;  //付费会员办卡时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date paygrade_end;  //付费会员到期日
    private Integer points_now;//现有积分

    public Cards() {
    }

    public Cards(String card_id, String user_id, String paygrade_id, String freegrade_id, Double consum_all, Date paygrade_start, Date paygrade_end, Integer points_now) {
        this.card_id = card_id;
        this.user_id = user_id;
        this.paygrade_id = paygrade_id;
        this.freegrade_id = freegrade_id;
        this.consum_all = consum_all;
        this.paygrade_start = paygrade_start;
        this.paygrade_end = paygrade_end;
        this.points_now = points_now;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "card_id='" + card_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", paygrade_id='" + paygrade_id + '\'' +
                ", freegrade_id='" + freegrade_id + '\'' +
                ", consum_all=" + consum_all +
                ", paygrade_start=" + paygrade_start +
                ", paygrade_end=" + paygrade_end +
                ", points_now=" + points_now +
                '}';
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

    public Integer getPoints_now() {
        return points_now;
    }

    public void setPoints_now(Integer points_now) {
        this.points_now = points_now;
    }
}

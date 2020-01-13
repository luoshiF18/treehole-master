package com.treehole.framework.domain.appointment;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName AppOrdetr
 * @Description: 预约订单
 * @Author XDD
 * @Date 2019/10/25
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "appointment")
public class AppOrder implements Serializable {
    // 预约单ID
    @Id
    private String id;

    // 用户ID
    @Column(name = "user_id")
    private String userId;

    // 咨询师ID
    @Column(name = "clt_id")
    private String cltId;

    // 预约日期
    @Column(name = "app_date")
    private Date appDate;

    // 预约咨询方式
    @Column(name = "app_mode")
    private Integer appMode;

    // 状态
    @Column(name = "app_status")
    private Integer appStatus;

    // 价格
    @Column(name = "app_price")
    private BigDecimal appPrice;

    // 创建时间
    @Column(name = "create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    // 创建时间
    @Column(name = "update_time")
    private Date updateTime;

    // 备注
    @Column(name = "app_note")
    private String appNote;
}

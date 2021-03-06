package com.treehole.framework.domain.appointment;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
@GenericGenerator(name ="jpa-uuid",strategy = "uuid")
public class AppOrder implements Serializable {
    // 预约单ID
    @Id
    @GeneratedValue(generator = "jpa-uuid")
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

    // 预约开始时间
    @Column(name = "app_start_time")
    private String appStartTime;

    // 预约结束时间
    @Column(name = "app_end_time")
    private String appEndTime;

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
    private Date createTime;

    // 创建时间
    @Column(name = "update_time")
    private Date updateTime;

    // 备注
    @Column(name = "app_note")
    private String appNote;
}

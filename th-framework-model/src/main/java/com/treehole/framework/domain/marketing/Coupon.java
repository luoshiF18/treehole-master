package com.treehole.framework.domain.marketing;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_coupon")
@ToString
public class Coupon implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String title;
    private String letter;
    private String icon;
    private Integer type;
    @Transient
    private String typeName;
    private String usedTypeId;//1测评 2咨询 3课程 0所有
    @Transient
    private String usedTypeName;
    private Boolean withSpecial;
    private BigDecimal withAmount;
    private BigDecimal usedAmount;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Boolean validType;
    @Transient
    private String validTypeName;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
    private Integer validDays;
    private Integer quota; // 发行量
    private Integer limitNum; // 限领
    private Integer usedBy;   // 受惠人群/适用人群 0:所有会员，1:付费会员，2:普通会员
    private Integer stock;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date created;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updated;
    private Integer status;   //优惠券状态 0:已结束，1：生效，2：已结束
    @Transient
    private String statusName;



}

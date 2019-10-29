package com.treehole.framework.domain.marketing;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_coupon")
public class Coupon implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String title;
    private String icon;
    private String typeId;
    private Boolean withSpecial;
    private BigDecimal withAmount;
    private BigDecimal usedAmount;
    private BigDecimal stepAmount;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Boolean validType;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
    private Integer validDays;
    private Integer quota; // 发行量
    private Integer limitNum; // 限领
    private Integer usedBy;   // 受惠人群/适用人群 null:所有会员，1:付费会员，2:普通会员
    private Integer stock;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date created;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updated;
    private Integer status;   //优惠券状态 0:已结束，1：生效，2：已结束
    private Boolean valid;   //删除状态码  0:已删除，1：

  //usedByStr,statusStr 的set方法



}

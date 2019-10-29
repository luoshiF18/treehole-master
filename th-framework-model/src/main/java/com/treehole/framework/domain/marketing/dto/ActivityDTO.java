package com.treehole.framework.domain.marketing.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
public class ActivityDTO {
    private String id;
    private String title;
    private String subTitle;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer discount;
    private BigDecimal fixedAmount;
    private BigDecimal reduction;
    private Integer limitNum;
    private Boolean withCoupon;
    private String couponId;
    private Integer point;
    private String typeId;
    private String images;
    private String goods;
    private String rule;
    private Integer status;
    private String description;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date created;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updated;
}

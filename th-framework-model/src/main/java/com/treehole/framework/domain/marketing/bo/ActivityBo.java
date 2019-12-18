package com.treehole.framework.domain.marketing.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
public class ActivityBo {
    private String id;
    private String title;
    private String subTitle;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer point;

    private String typeName;//

    private Integer discount;
    private BigDecimal fixedAmount;
    private BigDecimal reduction;
    private Integer limitNum;
    private Boolean withCoupon;
    private String couponId;

    private String images;
    private String rule;
    private Integer status;
    private String description;
}

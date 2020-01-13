package com.treehole.framework.domain.marketing.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
public class CouponBo {
    private String id;
    private String title;
    private String icon;
    private String typeName;//优惠券类型
    private String usedType;

    private BigDecimal withAmount;//门槛
    private BigDecimal usedAmount;//面额
    private Boolean withSpecial;
    private Boolean validType;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
    private Integer validDays;
    private Integer limitNum; // 限领
    private Integer usedBy;   // 受惠人群/适用人群 null:所有会员，1:付费会员，2:普通会员
    private Integer status;   //优惠券状态 0:已结束，1：生效，2：已结束

}

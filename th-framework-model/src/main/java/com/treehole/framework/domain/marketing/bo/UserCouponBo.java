package com.treehole.framework.domain.marketing.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
public class UserCouponBo {

    private String id;
    private String title;
    private String icon;
    private BigDecimal withAmount;//门槛
    private BigDecimal usedAmount;//面额
    private String typeName;//优惠券类型
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
    private Boolean status;//用户优惠券状态
    private Boolean unUsedStatus;
    private Boolean usedStatus;
    private String usedTypeName;

}

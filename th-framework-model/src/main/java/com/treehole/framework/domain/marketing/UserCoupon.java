package com.treehole.framework.domain.marketing;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_user_coupon")
public class UserCoupon {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String couponId;
    private String userId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
    private Boolean status;     //券状态  生效 未生效
    private Boolean unUsedStatus; // 未使用状态 已生效/已过期
    private Boolean usedStatus; //使用状态 已过期， 已使用
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date created;
}

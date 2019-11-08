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
@Table(name = "tb_activity")
public class Activity implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String title;
    private String letter;
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

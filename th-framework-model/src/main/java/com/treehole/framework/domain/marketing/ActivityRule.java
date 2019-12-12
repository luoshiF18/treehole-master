package com.treehole.framework.domain.marketing;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_activity_rule")
public class ActivityRule implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String activityId;
    private Integer type;
    private Integer discount;
    @Transient
    private BigDecimal fixedAmount;
    private BigDecimal reduction;
    private Integer limitNum;
    private Integer threshold;
    private BigDecimal withAmount;
    private Integer withNumber;
    private Integer point;
    private String pointDesc;

}

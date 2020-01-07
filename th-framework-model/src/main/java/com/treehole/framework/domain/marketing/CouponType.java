package com.treehole.framework.domain.marketing;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_coupon_type")
@ToString
public class CouponType implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String usedType;
    private Boolean superposition;
    private Boolean simultaneousUse;
    private String rule;
}

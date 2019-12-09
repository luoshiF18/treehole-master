package com.treehole.framework.domain.marketing;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_activity_goods")
public class ActivityGoods {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String activityId;
    private Integer resType;
    private String resId;
    private String resName;
    private String image;
    private BigDecimal price;
    private BigDecimal actualPrice;
}

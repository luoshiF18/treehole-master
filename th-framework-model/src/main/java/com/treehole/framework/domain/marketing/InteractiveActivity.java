package com.treehole.framework.domain.marketing;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_interactive_activity")
public class InteractiveActivity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String shortTitle;
    private String title;
    private String introduce;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date releaseBeginTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date releaseEndTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Boolean ifOnline;
    private String place;
    private Boolean ifPay;
    private BigDecimal payAmount;
    private Boolean ifLimitNum;
    private Integer limitNum;
    private String joinInMode;
    private String originatorName;
    private String originatorPhone;
    private String other;
}

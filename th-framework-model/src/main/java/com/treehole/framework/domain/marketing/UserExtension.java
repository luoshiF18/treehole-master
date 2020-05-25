package com.treehole.framework.domain.marketing;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_user_extension")
public class UserExtension implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String extensionId;
    private Boolean resolve;
    private String userId;
    private Integer via;
    private Integer mode;
    private String title;
    private String content;
    private Boolean usedFor;
    private Integer activityType;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;
}

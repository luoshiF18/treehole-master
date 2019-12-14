package com.treehole.framework.domain.marketing;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_email")
public class Email {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String title;
    private String content;
    private Boolean usedFor;
    private String resId;
    private Integer memberCount;
}

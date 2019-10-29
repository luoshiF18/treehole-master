package com.treehole.framework.domain.marketing;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wanglu
 */

@Data
@Table(name = "tb_type")
public class Type {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String name;
    private Boolean usedFor;
}

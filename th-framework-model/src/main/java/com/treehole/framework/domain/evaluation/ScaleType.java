package com.treehole.framework.domain.evaluation;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @auther: Yan Hao
 * @date: 2019/10/25
 */
@Data
@Table(name = "scale_type")
@NameStyle(Style.normal)
public class ScaleType {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String scaleType;
}

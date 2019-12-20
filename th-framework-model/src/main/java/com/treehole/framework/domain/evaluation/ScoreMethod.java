package com.treehole.framework.domain.evaluation;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 计算量表方法类
 * @auther: Yan Hao
 * @date: 2019/12/13
 */
@Data
@Table(name = "scale_score_method")
@NameStyle(Style.normal)
public class ScoreMethod {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String chinese;
    private String english;
}

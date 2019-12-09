package com.treehole.framework.domain.marketing;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author wanglu
 */
@Data
@Table(name = "tb_extension")
public class Extension {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private Integer mode;
    @Transient
    private String modeName;
    private String title;
    private String content;
    private Boolean usedFor; //false：优惠券 true：活动
    @Transient
    private String usedForStr;
    private String usedForId; //
    @Transient
    private List<String> to;
    private Integer succCount;
    private Integer count;
    private String url;
    @Transient
    private List<Map<String, String>> values;
    private String info;
}

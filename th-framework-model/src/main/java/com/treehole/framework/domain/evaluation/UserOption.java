package com.treehole.framework.domain.evaluation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户选项id
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Data
@Table(name = "user_option")
@NameStyle(Style.normal)
public class UserOption implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String scaleId;
    private String userId;
    private String questionAndOption;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date time;

}

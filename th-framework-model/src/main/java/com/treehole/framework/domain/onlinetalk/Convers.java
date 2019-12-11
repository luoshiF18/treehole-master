package com.treehole.framework.domain.onlinetalk;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 客服人员实体类
 * @Date
 */
@Data    //getter setter hashCode equals
@Table(name = "onlinetalk_convers")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class Convers implements Serializable {

    @Id //声明主键字段
    private String convers_id; //会话id
    private String convers_agentid; //客服id
    private String convers_userid; //用户id
    private String convers_agentname; //客服姓名
    private String convers_username; //用户姓名
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date begin_time; //开始时间


}


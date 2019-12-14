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
 * @author hewenze
 * @Description: 留言实体类
 * @Date
 */
@Data    //getter setter hashCode equals
@Table(name = "onlinetalk_leave")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class Leave implements Serializable {

        @Id //声明主键字段
        private String leave_id; //客服id
        private String phone; //客服姓名
        private String email; //客服账号
        private String name; //客服密码
        private String content;
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
        private Date createtime; //创建时间

    }


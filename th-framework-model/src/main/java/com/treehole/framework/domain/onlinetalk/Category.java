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
 * @Description: 快捷回复分类实体类
 * @Date
 */
@Data    //getter setter hashCode equals
@Table(name = "onlinetalk_category")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class Category implements Serializable {

        @Id //声明主键字段
        private String category_id; //分类id
        private String category_name; //分类名称
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
        private Date category_createtime; //创建时间
        private String category_creater;//创建者


    }


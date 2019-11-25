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
@Table(name = "onlinetalk_reply")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class Reply implements Serializable {

        @Id //声明主键字段
        private String reply_id; //回复id
        private String reply_title; //回复标题
        private String category_id; //分类id
        private String reply_content; //回复内容
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
        private Date reply_createtime; //创建时间
        private String reply_creater;//创建者


    }


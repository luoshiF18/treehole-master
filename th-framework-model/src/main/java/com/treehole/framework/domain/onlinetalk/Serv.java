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
 * @Description: 服务列表实体类
 * @Date
 */
@Data    //getter setter hashCode equals
@Table(name = "onlinetalk_serv")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class Serv implements Serializable {

        @Id //声明主键字段
        private String serv_id; //服务id
        private String agent_id; //客服id
        private String agent_name; //客服姓名
        private String user_id; //用户id
        private String user_name; //用户姓名
        private String serv_title; //服务标题
        private String serv_content; //服务内容
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
        private Date serv_time; //创建时间


    }


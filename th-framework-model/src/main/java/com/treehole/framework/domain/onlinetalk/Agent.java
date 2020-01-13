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
 * @Description: 客服人员实体类
 * @Date
 */
@Data    //getter setter hashCode equals
@Table(name = "onlinetalk_agent")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class Agent implements Serializable {

        @Id //声明主键字段
        private String agent_id; //客服id
        private String agent_name; //客服姓名
        private String agent_no; //客服账号
        private String agent_password; //客服密码
        private String agent_phone; //客服电话
        private String agent_from; //客服地址
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
        private Date create_time; //创建时间
        private String creater;//创建者
        private String agent_sex;//客服性别


    }


package com.treehole.framework.domain.onlinetalk.Vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 客服人员实体包装类
 * @Date
 */
@Data    //getter setter hashCode equals
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
public class AgentVo implements Serializable {

        private String agent_id; //客服id
        private String agent_name; //客服姓名
        private String agent_no; //客服账号
        private String agent_phone; //客服电话
        private String agent_from; //客服地址
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
        private Date create_time; //创建时间
        private String creater;//创建者
        private String agent_sex;//客服性别


    }


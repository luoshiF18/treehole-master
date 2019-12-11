package com.treehole.framework.domain.psychologist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Helay
 * @date 2019/11/23 9:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@NameStyle(Style.normal)
public class Comment implements Serializable {

    //评价记录id
    @Id
    private String comment_id;

    //用户id
    private String user_id;

    //咨询师id
    private String psychologist_id;

    //订单id
    private String order_id;

    //咨询记录id
    private String consultation_id;

    //评价类型
    private String comment_type;

    //用户对咨询师的评价内容
    private String comment_content;

    //评价信息生成时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time;

    //评价信息更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date update_time;

}

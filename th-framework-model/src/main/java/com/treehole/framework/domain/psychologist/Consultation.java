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
 * 用户咨询记录实体类
 *
 * @author Helay
 * @date 2019/11/19 10:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consultation")
@NameStyle(Style.normal)
public class Consultation implements Serializable {

    //咨询记录id
    @Id
    private String consultation_id;

    //心理咨询师id
    private String psychologist_id;

    //用户id
    private String user_id;

    //咨询师给出的意见id
    private String suggestion_id;

    //咨询类型
    private String type;

    //用户咨询时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date consultation_time;
}

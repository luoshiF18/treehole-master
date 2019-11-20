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

/**
 * 建议信息实体类
 *
 * @author Helay
 * @date 2019/11/19 10:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "suggestion")
@NameStyle(Style.normal)
public class Suggestion implements Serializable {

    //咨询师建议id
    @Id
    private String suggestion_id;

    //咨询师id
    private String psychologist_id;

    //用户病情描述信息
    private String description;

    //咨询师给出的意见信息
    private String suggestion_info;

    //心理治疗信息
    private String psychotherapy;

    //物理治疗信息
    private String physicotherapy;

    //咨询师给出的预警信息
    private String warning;

    //意见记录生成时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String create_time;
}

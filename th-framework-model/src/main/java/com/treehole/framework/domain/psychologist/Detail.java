package com.treehole.framework.domain.psychologist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Helay
 * @date 2019/11/16 8:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detail")
@NameStyle(Style.normal)
public class Detail {

    //咨询师id
    @Id
    private String psychologist_id;

    //咨询师姓名
    private String psychologist_name;

    //心理咨询师所属机构名
    private String organization_name;

    //机构地址
    private String organization_address;

    //咨询师获得的好评数
    private String praise_number;

    //好评等级
    private String praise_grade;

    //访问量
    private String visit_number;

    //入住平台年限
    private String platform_year;

    //留言数
    private String message;

    //详情信息创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time;

    //详情信息更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date update_time;

}

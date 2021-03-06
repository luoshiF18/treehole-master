package com.treehole.framework.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 签到信息
 * @Date
 */
@Data
@Table(name = "member_checkin")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class Checkin implements Serializable {
    private String checkin_id; //签到id
    private String user_id; //用户id
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date checkin_time; //签到时间


}

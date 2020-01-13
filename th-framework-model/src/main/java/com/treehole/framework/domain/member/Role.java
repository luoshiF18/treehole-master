package com.treehole.framework.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 角色实体类
 * @Date
 */
@Data
@Table(name = "member_role")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class Role {
    @Id
    private String role_id;  //角色Id
    private String role_name;  //角色名称
    private String role_code;  //角色code
    private String description;  //描述
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date create_time;  //创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date update_time;  //更新时间
    private String status; //角色状态


}

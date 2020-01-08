package com.treehole.framework.domain.member.Vo;

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
public class PermissionVo {
    @Id
    private String id;  //Id
    private String role_id;  //角色名称
    private Integer menu_id;  //菜单id
    private String role_name;  //角色名称
    private String menu_name;  //菜单id
    private Integer menu_pid;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date create_time;  //创建时间
}

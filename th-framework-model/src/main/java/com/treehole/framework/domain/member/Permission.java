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
@Table(name = "th_permission")
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.normal)
public class Permission {
    @Id
    private String id;  //Id
    private String role_id;  //角色名称
    private Integer menu_id;  //菜单id
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date create_time;  //创建时间
}

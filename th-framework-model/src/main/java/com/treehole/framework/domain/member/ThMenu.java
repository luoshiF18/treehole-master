package com.treehole.framework.domain.member;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Qbl
 * Created by 10:48 on 2019/11/15.
 * Version: 1.0
 */
@Data
@ToString
@Entity
@Table(name = "th_menu")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ThMenu {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String code;
    @Column(name="p_code")
    private String pCode;
    @Column(name="p_id")
    private String pId;
    @Column(name="menu_name")
    private String menuName;
    private String url;
    @Column(name="is_menu")
    private String isMenu;
    private Integer level;
    private Integer sort;
    private Integer status;
    private String icon;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;

}

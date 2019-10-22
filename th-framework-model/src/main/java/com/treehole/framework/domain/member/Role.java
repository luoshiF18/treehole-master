package com.treehole.framework.domain.member;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 角色实体类
 * @Date
 */
@Data
@Table(name = "member_role")
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

    public Role() {
    }

    public Role(String role_id, String role_name, String role_code, String description, Date create_time, Date update_time, String status) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.role_code = role_code;
        this.description = description;
        this.create_time = create_time;
        this.update_time = update_time;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id='" + role_id + '\'' +
                ", role_name='" + role_name + '\'' +
                ", role_code='" + role_code + '\'' +
                ", description='" + description + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", status='" + status + '\'' +
                '}';
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

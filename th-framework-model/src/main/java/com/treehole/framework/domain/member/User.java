package com.treehole.framework.domain.member;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员实体类
 * @Date
 */

@Data
@Table(name = "member_user")
public class User implements Serializable {
    @Id //声明主键字段
    //@KeySql(useGeneratedKeys = true) //该主键是否自增
    private String user_id; //会员id
    private String uniq_id; //会员唯一id
    private String role_id; //用户角色权限 0：用户 1：管理员
    private String user_image;//会员头像
    private String user_nickname; //会员昵称
    private String user_name; //用户姓名
    private String password; //用户密码
    private Integer gender; //用户性别 0：男 1：女
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date user_birth; //用户出生年月
    private String user_email; //用户邮箱
    private String user_phone; //用户电话
    private String user_qq; //用户qq
    private String user_wechat; //用户微信
    private String user_region; //用户所在地区
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date user_createtime; //用户创建账户时间
    private Integer user_type; //用户类型 0：个人用户 1：企业用户
    private Integer user_status; //用户状态 0：正常 1：被禁
    //private Integer points_now;  //现有积分



    public User() {
    }

    public User(String user_id, String uniq_id, String role_id, String user_image, String user_nickname, String user_name, String password, Integer gender, Date user_birth, String user_email, String user_phone, String user_qq, String user_wechat, String user_region, Date user_createtime, Integer user_type, Integer user_status, Integer points_now) {
        this.user_id = user_id;
        this.uniq_id = uniq_id;
        this.role_id = role_id;
        this.user_image = user_image;
        this.user_nickname = user_nickname;
        this.user_name = user_name;
        this.password = password;
        this.gender = gender;
        this.user_birth = user_birth;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_qq = user_qq;
        this.user_wechat = user_wechat;
        this.user_region = user_region;
        this.user_createtime = user_createtime;
        this.user_type = user_type;
        this.user_status = user_status;
        //this.points_now = points_now;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", uniq_id='" + uniq_id + '\'' +
                ", role_id='" + role_id + '\'' +
                ", user_image='" + user_image + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", user_birth=" + user_birth +
                ", user_email='" + user_email + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_qq='" + user_qq + '\'' +
                ", user_wechat='" + user_wechat + '\'' +
                ", user_region='" + user_region + '\'' +
                ", user_createtime=" + user_createtime +
                ", user_type=" + user_type +
                ", user_status=" + user_status +
                //", points_now=" + points_now +
                '}';
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUniq_id() {
        return uniq_id;
    }

    public void setUniq_id(String uniq_id) {
        this.uniq_id = uniq_id;
    }
    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(Date user_birth) {
        this.user_birth = user_birth;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_qq() {
        return user_qq;
    }

    public void setUser_qq(String user_qq) {
        this.user_qq = user_qq;
    }

    public String getUser_wechat() {
        return user_wechat;
    }

    public void setUser_wechat(String user_wechat) {
        this.user_wechat = user_wechat;
    }

    public String getUser_region() {
        return user_region;
    }

    public void setUser_region(String user_region) {
        this.user_region = user_region;
    }

    public Date getUser_createtime() {
        return user_createtime;
    }

    public void setUser_createtime(Date user_createtime) {
        this.user_createtime = user_createtime;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    /*public Integer getPoints_now() {
        return points_now;
    }

    public void setPoints_now(Integer points_now) {
        this.points_now = points_now;
    }*/
}

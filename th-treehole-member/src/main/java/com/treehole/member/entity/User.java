package com.treehole.member.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员实体类
 * @Date
 */
public class User implements Serializable {
    private String user_id; //会员id
    private String user_image;//会员头像
    private String user_nickname; //会员昵称
    private String user_name; //用户姓名
    private String password; //用户密码
    private int gender; //用户性别 0：男 1：女
    private Date user_birth; //用户出生年月
    private String user_email; //用户邮箱
    private String user_phone; //用户电话
    private String user_qq; //用户qq
    private String user_wechat; //用户微信
    private String region; //用户所在地区
    private Date user_createtime; //用户创建账户时间
    private int user_type; //用户类型 0：个人用户 1：企业用户
    private int user_status; //用户状态 0：正常 1：被禁
    private int user_role; //用户角色/权限 0：用户 1：管理员

    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getUser_createtime() {
        return user_createtime;
    }

    public void setUser_createtime(Date user_createtime) {
        this.user_createtime = user_createtime;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }
}

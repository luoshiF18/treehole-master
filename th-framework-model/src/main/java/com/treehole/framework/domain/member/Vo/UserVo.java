package com.treehole.framework.domain.member.Vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员实体扩展类
 * @Date
 */
public class UserVo implements Serializable {
    private String uniq_id; //会员唯一id
    private String user_image;//会员头像
    private String user_nickname; //会员昵称
    private String user_name; //用户姓名
    private Integer gender; //用户性别 0：男 1：女
    private Date user_birth; //用户出生年月
    private String user_email; //用户邮箱
    private String user_phone; //用户电话
    private String user_qq; //用户qq
    private String user_wechat; //用户微信
    private String user_region; //用户所在地区
    private Integer points_now;  //现有积分

    public UserVo() {
    }

    public UserVo(String uniq_id, String user_image, String user_nickname, String user_name, Integer gender, Date user_birth, String user_email, String user_phone, String user_qq, String user_wechat, String user_region, Integer points_now) {
        this.uniq_id = uniq_id;
        this.user_image = user_image;
        this.user_nickname = user_nickname;
        this.user_name = user_name;
        this.gender = gender;
        this.user_birth = user_birth;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_qq = user_qq;
        this.user_wechat = user_wechat;
        this.user_region = user_region;
        this.points_now = points_now;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "uniq_id='" + uniq_id + '\'' +
                ", user_image='" + user_image + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_name='" + user_name + '\'' +
                ", gender=" + gender +
                ", user_birth=" + user_birth +
                ", user_email='" + user_email + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_qq='" + user_qq + '\'' +
                ", user_wechat='" + user_wechat + '\'' +
                ", user_region='" + user_region + '\'' +
                ", points_now=" + points_now +
                '}';
    }

    public String getUniq_id() {
        return uniq_id;
    }

    public void setUniq_id(String uniq_id) {
        this.uniq_id = uniq_id;
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

    public Integer getPoints_now() {
        return points_now;
    }

    public void setPoints_now(Integer points_now) {
        this.points_now = points_now;
    }

}

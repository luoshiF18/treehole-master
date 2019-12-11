package com.treehole.framework.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员实体类
 * @Date
 */

@Data    //getter setter hashCode equals
@Table(name = "member_user")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)   //数据库字段与属性名写法保持一致
public class User implements Serializable {
    @Id //声明主键字段
    private String user_id; //会员id
    //private String uniq_id; //会员唯一id
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
    private String company_id;//企业用户所在公司





}

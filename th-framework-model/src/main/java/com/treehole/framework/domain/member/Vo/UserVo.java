package com.treehole.framework.domain.member.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 会员实体扩展类
 * @Date
 */

@Data    //getter setter hashCode equals
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {
    private String user_id;
    //private String uniq_id; //会员唯一id
    private String role_name;//角色名称
    private String user_image;//会员头像
    private String user_nickname; //会员昵称
    private String user_name; //用户姓名
    private Integer gender; //用户性别 0：男 1：女
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date user_birth; //用户出生年月
    private int age;
    private String user_email; //用户邮箱
    private String user_phone; //用户电话
    private String user_qq; //用户qq
    private String user_wechat; //用户微信
    private String user_region; //用户所在地区
    //private Integer points_now;  //现有积分
    private Date user_createtime; //用户创建账户时间
    private String company_id;//企业用户所在公司
    private String user_status; //用户状态 0：正常 1：被禁
    private String user_type; //用户类型 0：个人用户 1：企业用户


}

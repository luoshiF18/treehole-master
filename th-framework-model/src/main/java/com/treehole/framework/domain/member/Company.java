package com.treehole.framework.domain.member;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Shan HuiJie
 * @Description: 公司类
 * @Date 2019.12.23 17:44
 */
@Data    //getter setter hashCode equals
@Table(name = "member_company")
@NoArgsConstructor //是生成一个无参的构造函数
@AllArgsConstructor //生成一个有参构造函数
@NameStyle(Style.normal)
public class Company implements Serializable {
    @Id //声明主键字段
    private String id;
    @NotBlank(message = "联系人姓名不能为空")
    private String linkname;//联系人名称
    @NotBlank(message = "公司名称不能为空")   //@NotBlank只应用于字符串且在比较时会去除字符串的空格
    private String name; //公司名称
    @NotBlank(message = "联系人手机号不能为空")
    private String mobile;//手机号
    @NotBlank(message = "公司邮箱不能为空")
    private String email;
    @NotBlank(message = "user_id不能为空")
    private String user_id;
    private String intro;//公司简介
    private String logo;
    private String identitypic;//身份证照片
    private String worktype;//工作性质
    private String businesspic;//营业执照
    private Integer status;//企业状态 1异常 0正常
    private Integer checkstatus;//审核状态 1未审核 0审核通过
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date check_time; //审核时间
    private String check_person;  //审核人
    private  Date apply_time; //申请时间
}

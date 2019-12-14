package com.treehole.framework.domain.evaluation.vo;

import com.treehole.framework.domain.evaluation.Warning;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Qbl
 * Created by 8:51 on 2019/10/25.
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarningVo extends Warning implements Serializable{

    private String scaleName;
    private String userName; //实际姓名，user表中的
    private String userNickName; //昵称，user表中的
    private String sex;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date userBirth;
    @Null
    private String phone;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date1;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date2;

}

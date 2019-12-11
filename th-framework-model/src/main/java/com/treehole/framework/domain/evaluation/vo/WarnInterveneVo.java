package com.treehole.framework.domain.evaluation.vo;

import com.treehole.framework.domain.member.Vo.UserVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Qbl
 * Created by 8:54 on 2019/11/25.
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class WarnInterveneVo extends UserVo {

    private String id;
    private String userName;
    private String advisoryName;
    private Date recordTime;
    private Integer status;

}

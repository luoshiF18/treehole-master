package com.treehole.framework.domain.evaluation.vo;

import com.treehole.framework.domain.evaluation.Option;
import lombok.Data;

import java.util.List;

/**
 * 用于更改题目
 *
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@Data
public class QuestionVO3 {

    private String id;
    private String scaleId;
    private Integer sort;
    private String question;
/*    private String createTime;
    private String createUserId;
    private String updateTime;
    private String updateUserId;
    private String remark;*/
    //    选项全部信息
    List<Option> options;
}

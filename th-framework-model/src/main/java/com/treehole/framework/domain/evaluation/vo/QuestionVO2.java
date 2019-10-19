package com.treehole.framework.domain.evaluation.vo;

import lombok.Data;

import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/17
 */
@Data
public class QuestionVO2 {

    private String nextQuestionId;
    private Integer questionSort;
    private String question;
    //返回每次的选项
    private List<OptionVO> optionVOS;
}

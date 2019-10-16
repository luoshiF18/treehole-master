package com.treehole.framework.domain.evaluation.vo;

import lombok.Data;

import java.util.List;

/**
 * 问题展示类，用于TestDetailVO
 *
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@Data
public class QuestionVO {

    private String id;
    private Integer sort;
    private String question;
    //    该问题下所有的选项
    private List<OptionVO> optionVOS;
}

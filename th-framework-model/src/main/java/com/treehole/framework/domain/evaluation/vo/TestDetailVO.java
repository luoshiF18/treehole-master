package com.treehole.framework.domain.evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 做题展示类,展示给前台
 *
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDetailVO {

    private String id;
    private String scaleName;
    private String shortName;
    private String images;
    private String type;
    //    问题集合，里面包含所有问题和其下面的所有选项
    private List<QuestionVO> questionVOS;
}

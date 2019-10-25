package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.QuestionVO2;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther: Yan Hao
 * @date: 2019/10/17
 */
@Data
@NoArgsConstructor
public class StartTestResult2 extends ResponseResult {
    QuestionVO2 questionVO2;

    public StartTestResult2(ResultCode resultCode, QuestionVO2 questionVO2) {
        super(resultCode);
        this.questionVO2 = questionVO2;
    }
}
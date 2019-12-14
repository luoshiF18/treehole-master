package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.QuestionVO;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@Data
@NoArgsConstructor
public class StartTestResult extends ResponseResult {

    QuestionVO questionVO;

    public StartTestResult(ResultCode resultCode, QuestionVO questionVO) {
        super(resultCode);
        this.questionVO = questionVO;
    }
}

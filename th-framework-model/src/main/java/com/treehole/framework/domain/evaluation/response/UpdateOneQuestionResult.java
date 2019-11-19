package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.QuestionVO3;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @auther: Yan Hao
 * @date: 2019/11/5
 */
@Data
@NoArgsConstructor
public class UpdateOneQuestionResult extends ResponseResult {
    QuestionVO3 questionVO3;

    public UpdateOneQuestionResult(ResultCode resultCode, QuestionVO3 questionVO3) {
        super(resultCode);
        this.questionVO3 = questionVO3;
    }
}


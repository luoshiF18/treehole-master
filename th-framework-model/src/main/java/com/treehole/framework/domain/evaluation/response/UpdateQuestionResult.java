package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.QuestionVO3;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@Data
@NoArgsConstructor
public class UpdateQuestionResult extends ResponseResult {

    List<QuestionVO3> questionVO3;

    public UpdateQuestionResult(ResultCode resultCode, List<QuestionVO3> questionVO3) {
        super(resultCode);
        this.questionVO3 = questionVO3;
    }
}

package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.TestDetailVO;
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

    TestDetailVO testDetailVO;

    public StartTestResult(ResultCode resultCode, TestDetailVO testDetailVO) {
        super(resultCode);
        this.testDetailVO = testDetailVO;
    }
}

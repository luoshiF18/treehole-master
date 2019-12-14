package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.ResultVO;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther: Yan Hao
 * @date: 2019/10/16
 */
@Data
@NoArgsConstructor
public class ResultRequest extends ResponseResult {
    ResultVO resultVO;

    public ResultRequest(ResultCode resultCode, ResultVO resultVO) {
        super(resultCode);
        this.resultVO = resultVO;
    }
}

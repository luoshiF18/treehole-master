package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.ScaleDetailVO;
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
public class DetailResult extends ResponseResult {
    ScaleDetailVO scaleDetail;

    public DetailResult(ResultCode resultCode, ScaleDetailVO scaleDetail) {
        super(resultCode);
        this.scaleDetail = scaleDetail;
    }
}

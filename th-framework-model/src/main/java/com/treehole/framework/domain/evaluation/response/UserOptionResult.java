package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.domain.evaluation.vo.UserOptionVO;
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
public class UserOptionResult extends ResponseResult {

    UserOptionVO userOptionVO;

    public UserOptionResult(ResultCode resultCode, UserOptionVO userOptionVO) {
        super(resultCode);
        this.userOptionVO = userOptionVO;
    }
}

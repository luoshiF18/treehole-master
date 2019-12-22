package com.treehole.framework.domain.onlineCourse.response;

import com.treehole.framework.domain.onlineCourse.Teachplan;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;


@Data
public class TeachplanResult extends ResponseResult {
    Teachplan teachplan;
    public TeachplanResult(ResultCode resultCode, Teachplan teachplan) {
        super(resultCode);
        this.teachplan = teachplan;
    }
}

package com.treehole.framework.domain.onlineCourse.response;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;


@Data
public class CourseBaseResult extends ResponseResult {
    CourseBase courseBase;
    public CourseBaseResult(ResultCode resultCode, CourseBase courseBase) {
        super(resultCode);
        this.courseBase = courseBase;
    }
}

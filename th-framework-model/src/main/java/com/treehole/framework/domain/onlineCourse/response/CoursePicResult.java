package com.treehole.framework.domain.onlineCourse.response;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CoursePic;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;


@Data
public class CoursePicResult extends ResponseResult {
    CoursePic coursePic;
    public CoursePicResult(ResultCode resultCode, CoursePic coursePic) {
        super(resultCode);
        this.coursePic = coursePic;
    }
}

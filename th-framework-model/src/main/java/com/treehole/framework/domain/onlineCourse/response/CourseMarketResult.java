package com.treehole.framework.domain.onlineCourse.response;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CourseMarket;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;


@Data
public class CourseMarketResult extends ResponseResult {
    CourseMarket courseMarket;
    public CourseMarketResult(ResultCode resultCode, CourseMarket courseMarket) {
        super(resultCode);
        this.courseMarket = courseMarket;
    }
}

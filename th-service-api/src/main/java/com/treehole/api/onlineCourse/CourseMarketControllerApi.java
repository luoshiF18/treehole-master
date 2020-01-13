package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CourseMarket;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.CourseMarketResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="在线课程接口管理",description = "提供在线课程课程营销操作")
public interface CourseMarketControllerApi {

    @ApiOperation("根据id查询课程营销信息")
    public CourseMarketResult findCourseMarketById(String courseId);

    @ApiOperation("更新课程营销信息，没有则添加")
    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket);
}

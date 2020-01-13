package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.Teachplan;
import com.treehole.framework.domain.onlineCourse.ext.TeachplanNode;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="在线课程接口管理",description = "提供在线课程基本信息操作")
public interface CourseBaseControllerApi {

    @ApiOperation("查询课程基本信息")
    public CourseBaseResult findCourseBaseById(String courseId);

    @ApiOperation("更新课程基本信息，没有则添加")
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase);

    @ApiOperation("添加课程基本信息")
    public ResponseResult addCourseBase(CourseBase courseBase);
}

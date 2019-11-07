package com.treehole.api.train;

import com.treehole.framework.domain.train.Course;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value="课程信息管理页面管理接口",description = "课程信息管理页面管理接口，提供页面的增、删、改、查")
public interface CourseControllerApi {

    @ApiOperation("添加课程信息")
    public ResponseResult addCourse(Course course);
    @ApiOperation("删除课程信息")
    public ResponseResult deleteCourse(String courseId);
    @ApiOperation("修改课程信息")
    public ResponseResult updateCourse(String courseId,Course course);
    @ApiOperation("通过id和模糊查询 查询课程信息")
    public QueryResponseResult<Course> findCourseByFuzzyQuery(int page ,Course course);
    @ApiOperation("查询所有课程信息")
    public QueryResponseResult<Course> findAllCourse(int page);




}

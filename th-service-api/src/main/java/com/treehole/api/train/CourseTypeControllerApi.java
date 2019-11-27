package com.treehole.api.train;

import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.CourseType;
import com.treehole.framework.domain.train.ext.ClassHeadmaster;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="课程类型信息管理页面管理接口",description = "课程类型信息管理页面管理接口，提供页面的增、删、改、查")
public interface CourseTypeControllerApi {

    @ApiOperation("添加课程类型信息")
    public ResponseResult addCourseType(CourseType courseType);
    @ApiOperation("删除课程类型信息")
    public ResponseResult deleteCourseType(String CourseTypeId);
    @ApiOperation("修改课程类型信息")
    public ResponseResult updateCourseType(String CourseTypeId,CourseType courseType);
    @ApiOperation("通过id和模糊查询 查询课程类型信息")
    public QueryResponseResult<CourseType> findCourseTypeByFuzzyQuery(int page , int size, CourseType courseType);

}

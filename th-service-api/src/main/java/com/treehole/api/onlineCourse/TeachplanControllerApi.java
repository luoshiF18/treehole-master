package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.Teachplan;
import com.treehole.framework.domain.onlineCourse.ext.TeachplanNode;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.TeachplanResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="在线课程接口管理",description = "提供在线课程计划操作")
public interface TeachplanControllerApi {

    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("根据课程计划id查询课程计划")
    public TeachplanResult getTeachplanById(String teachplanId);

    @ApiOperation("删除课程计划")
    public ResponseResult delTeachplan(String teachplanId);
}

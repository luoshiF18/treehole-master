package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.CoursePub;
import com.treehole.framework.domain.onlineCourse.CourseSearchParam;
import com.treehole.framework.domain.onlineCourse.TeachplanMediaPub;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="课程搜索",description = "课程搜索")
public interface CourseSearchControllerApi {

    @ApiOperation("课程搜索")
    public QueryResponseResult<CoursePub> searchCourse(int page, int size, CourseSearchParam courseSearchParam);

    @ApiOperation("根据id查询课程信息")
    public Map<String,CoursePub> getall(String id);

    @ApiOperation("根据课程计划查询媒资信息")
    public TeachplanMediaPub getmedia(String teachplanId);
}

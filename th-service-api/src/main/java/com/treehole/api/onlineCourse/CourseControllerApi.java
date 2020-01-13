package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.TeachplanMedia;
import com.treehole.framework.domain.onlineCourse.ext.CourseView;
import com.treehole.framework.domain.onlineCourse.request.QueryPageRequest;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.CoursePublishResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="在线课程接口管理",description = "提供在线课程操作")
public interface CourseControllerApi {

   @ApiOperation("查询课程视图信息")
   public CourseView findCourseView(String courseId);

   @ApiOperation("发布课程")
   public CoursePublishResult publishCourse(String courseId);

   @ApiOperation("分页查询课程")
    public QueryResponseResult findCourse(int page, int size, QueryPageRequest queryPageRequest);

   @ApiOperation("关联课程与媒资信息")
   public ResponseResult savemedia(TeachplanMedia teachplanMedia);
}

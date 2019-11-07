package com.treehole.api.train;

import com.treehole.framework.domain.train.ClassCourse;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value="班级课程关系接口",description = "班级课程关系接口")
public interface ClassCourseControllerApi {

    @ApiOperation("班级选课")
    public ResponseResult AddClassCourse(ClassCourse classCourse);
    @ApiOperation("班级课程删除")
    public ResponseResult deleteClassCourse(String classId, String courseId);

}

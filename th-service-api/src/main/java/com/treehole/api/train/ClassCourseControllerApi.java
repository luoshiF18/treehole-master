package com.treehole.api.train;

import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.ext.CourseList;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.SelectCourses;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value="班级课程关系接口",description = "班级课程关系接口")
public interface ClassCourseControllerApi {

    @ApiOperation("班级选课")
    public ResponseResult addClassCourse(String classId , CourseList courseList);
    @ApiOperation("班级课程删除")
    public ResponseResult deleteClassCourse(String classId, String courseId);
    @ApiOperation("查询需要进行选择的课程")
    public QueryResponseResult<CourseTeacher> findCourseByFuzzyQuery(int page , int size , String classId , SelectCourses selectCourses);

}

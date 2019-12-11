package com.treehole.api.train;

import com.treehole.framework.domain.train.ext.TeacherCourseShow;
import com.treehole.framework.domain.train.ext.TeacherSelectCourseList;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="老师选课管理页面管理接口",description = "老师选课管理页面管理接口，提供页面的显示课程、选课、退课")
public interface TeacherCourseApi {


    @ApiOperation("显示老师将要选择的课程")
    public QueryResponseResult<TeacherCourseShow> findTeacherCourse(int page , int size,TeacherCourseShow teacherCourseShow);

    @ApiOperation("老师选课")
    public ResponseResult teacherCourseSelection(String teacherId , TeacherSelectCourseList teacherSelectCourseList);

    @ApiOperation("所审核的课程")
    public QueryResponseResult<TeacherCourseShow> findExamineTeacherCourse (int page , int size,TeacherCourseShow teacherCourseShow);

    @ApiOperation("老师选课审核通过")
    public ResponseResult examinePass(String id);


    @ApiOperation("老师选课审核不通过")
    public ResponseResult examineNoPass(String id);

    @ApiOperation("查询审核情况")
    public QueryResponseResult<TeacherCourseShow> findExamineSituation(int page , int size,TeacherCourseShow teacherCourseShow);


}

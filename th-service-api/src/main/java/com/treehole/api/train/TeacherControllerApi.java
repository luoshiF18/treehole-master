package com.treehole.api.train;

import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.ClassHeadmaster;
import com.treehole.framework.domain.train.ext.TeacherCourseOfTeach;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value="老师信息管理页面管理接口",description = "老师信息管理页面管理接口，提供页面的增、删、改、查")
public interface TeacherControllerApi {

    @ApiOperation("添加老师信息")
    public ResponseResult addTeacher(Teacher teacher);
    @ApiOperation("删除老师信息")
    public ResponseResult deleteTeacher(String teacherId);
    @ApiOperation("修改老师信息")
    public ResponseResult updateTeacher(String teacherId,Teacher teacher);
    @ApiOperation("通过id和模糊查询查询老师信息")
    public QueryResponseResult<Teacher> findTeacherByFuzzyQuery(int page,int size, Teacher teacher);
    @ApiOperation("查询老师所教的班级")
    public QueryResponseResult<ClassHeadmaster> findTeacherClass(int page,int size, ClassHeadmaster classHeadmaster);
    @ApiOperation("查询老师所教的课程")
    public QueryResponseResult<TeacherCourseOfTeach> findTeacherCourseOfTeaching(int page,int size, TeacherCourseOfTeach teacherCourseOfTeach);
    @ApiOperation("查询班主任所带的班级")
    public QueryResponseResult<Class>findClassOfHeadTeacher(int page, int size, ClassHeadmaster classHeadmaster);





}

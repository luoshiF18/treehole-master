package com.treehole.api.train;

import com.treehole.framework.domain.train.*;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.ext.*;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="班级信息管理页面管理接口",description = "班级信息管理页面管理接口，提供页面的增、删、改、查")
public interface ClassControllerApi {

    @ApiOperation("添加班级信息")
    public ResponseResult addClass(Class class1);
    @ApiOperation("删除班级信息")
    public ResponseResult deleteClass(String classId);
    @ApiOperation("修改班级信息")
    public ResponseResult updateClass(String classId,Class class1);
    @ApiOperation("通过id和模糊查询 查询班级信息")
    public QueryResponseResult<ClassHeadmaster> findClassByFuzzyQuery(int page , int size, Class class1);


    @ApiOperation("班级学生统计")
    public QueryResponseResult<Student> findClassStudent(int page, int size, StudentExt studentExt);
    @ApiOperation("班级课程统计")
    public QueryResponseResult<CourseTeacher> findClassCourse(int page, int size, CourseExt courseExt);
    @ApiOperation("班级老师统计")
    public QueryResponseResult<Teacher> findClassTeacher(int page, int size, ClassTeacher classTeacher);

}

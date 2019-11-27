package com.treehole.api.train;

import com.treehole.framework.domain.train.Teacher;
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

}

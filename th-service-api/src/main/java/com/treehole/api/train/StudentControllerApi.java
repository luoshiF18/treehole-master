package com.treehole.api.train;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.ext.StudentCourse;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="学生信息管理页面管理接口",description = "学生信息管理页面管理接口，提供页面的增、删、改、查")
public interface StudentControllerApi {

    @ApiOperation("添加学生信息")
    public ResponseResult addStudent(Student student);
    @ApiOperation("删除学生信息")
    public ResponseResult deleteStudent(String studentId);
    @ApiOperation("修改学生信息")
    public ResponseResult updateStudent(String studentId,Student student);
    @ApiOperation("根据id和模糊查询学生信息")
    public QueryResponseResult<Student> findStudentByFuzzyQuery(int page , Student student);
    @ApiOperation("查询所有学生信息")
    public QueryResponseResult<Student> findAllStudent(int page);


    @ApiOperation("学生课程统计")
    public QueryResponseResult<StudentCourse> findStudentCourse(int page,String studentId);
    @ApiOperation("学生交费记录")
    public QueryResponseResult<Cost> findPay(int page,String studentId);
    @ApiOperation("学生欠费记录")
    public QueryResponseResult<Cost> findArrears(int page,String studentId);




}

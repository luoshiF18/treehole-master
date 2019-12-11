package com.treehole.api.train;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.StudentCourseParams;
import com.treehole.framework.domain.train.ext.StudentExt;
import com.treehole.framework.domain.train.ext.TeacherExt;
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
    public QueryResponseResult<StudentExt> findStudentByFuzzyQuery(int page , int size, Student student);
    @ApiOperation("学生毕业（个人）")
    public ResponseResult studentGraduation(String studentId);
    @ApiOperation("学生毕业（全班）")
    public ResponseResult studentGraduationAllClass(String classId);

    @ApiOperation("学生课程统计")
    public QueryResponseResult<CourseTeacher> findStudentCourse(int page, int size, StudentCourseParams studentCourseParams);
    @ApiOperation("学生老师统计")
    public QueryResponseResult<Teacher> findStudentTeacher(int page, int size, TeacherExt teacherExt);
    @ApiOperation("学生交费记录(欠费记录)")
    public QueryResponseResult<Cost> findPay(int page,int size,Cost cost);




}

package com.treehole.train.controller;
import com.treehole.api.train.StudentControllerApi;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.StudentCourseParams;
import com.treehole.framework.domain.train.ext.StudentExt;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/train/student")
public class StudentController implements StudentControllerApi {

    @Autowired
    StudentService studentService;

    @Override
    @PostMapping("/add")
    public ResponseResult addStudent(@RequestBody  Student student) {
       return studentService.addStudent(student);
    }

    @Override
    @DeleteMapping("delete/{studentId}")
    public ResponseResult deleteStudent(@PathVariable("studentId") String studentId) {
        return studentService.deleteStudent(studentId);
    }

    @Override
    @PutMapping("/update/{studentId}")
    public ResponseResult updateStudent(@PathVariable("studentId") String studentId,@RequestBody Student student) {
        return studentService.updateStudent(studentId,student);
    }

    @Override
    @PostMapping("/find/{page}/{size}")
    public QueryResponseResult<StudentExt> findStudentByFuzzyQuery(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody Student student) {
        return studentService.findStudentByFuzzyQuery(page,size,student);
    }



    @Override
    @PutMapping("/graduation/{studentId}")
    public ResponseResult studentGraduation(@PathVariable("studentId") String studentId) {
        return studentService.studentGraduation(studentId);
    }

    @Override
    @PutMapping("/graduationAll/{classId}")
    public ResponseResult studentGraduationAllClass(@PathVariable("classId") String classId) {
        return studentService.studentGraduationAllClass(classId);
    }


    //查看学生课程
    @Override
    @PostMapping("/findstudntcourse/{page}/{size}")
    public QueryResponseResult<CourseTeacher> findStudentCourse(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody StudentCourseParams studentCourseParams) {
        return studentService.findStudentCourse(page,size,studentCourseParams);
    }
    //查新学生交费记录(欠费记录)
    @Override
    @PostMapping("/findPay/{page}/{size}")
    public QueryResponseResult<Cost> findPay(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody Cost cost) {
        return studentService.findPay(page,size,cost);
    }


}

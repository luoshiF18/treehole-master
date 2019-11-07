package com.treehole.train.controller;
import com.treehole.api.train.StudentControllerApi;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.ext.StudentCourse;
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
    @PostMapping("/find/{page}")
    public QueryResponseResult<Student> findStudentByFuzzyQuery(@PathVariable("page") int page ,@RequestBody Student student) {
        return studentService.findStudentByFuzzyQuery(page,student);
    }

    @Override
    @GetMapping("/findall/{page}")
    public QueryResponseResult<Student> findAllStudent(int page) {
        return studentService.findAllStudent(page);
    }


    //查看学生课程
    @Override
    @GetMapping("/findstudntcourse/{page}/{size}/{studentId}")
    public QueryResponseResult<StudentCourse> findStudentCourse(@PathVariable("page") int page,@PathVariable("studentId") String studentId) {
        return studentService.findStudentCourse(page,studentId);
    }
    //查新学生交费记录
    @Override
    @GetMapping("/findPay/{page}/{size}/{studentId}")
    public QueryResponseResult<Cost> findPay(@PathVariable("page") int page,@PathVariable("studentId")String studentId) {
        return studentService.findPay(page,studentId);
    }

    //查新学生欠费记录
    @Override
    @GetMapping("/findarrears/{page}/{size}/{studentId}")
    public QueryResponseResult<Cost> findArrears(@PathVariable("page") int page,@PathVariable("studentId")String studentId) {
        return studentService.findArrears(page,studentId);
    }

}

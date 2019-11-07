package com.treehole.train.controller;

import com.treehole.api.train.ClassControllerApi;
import com.treehole.framework.domain.train.*;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.ext.StudentCourse;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/class")
public class ClassController implements ClassControllerApi {
   @Autowired
    ClassService classService;

    @Override
    @PostMapping("/add")
    public ResponseResult addClass(@RequestBody Class class1) {
        return classService.addClass(class1);
    }

    @Override
    @DeleteMapping("/delete/{classId}")
    public ResponseResult deleteClass(@PathVariable("classId") String classId) {
        return classService.deleteClass(classId);
    }

    @Override
    @PutMapping("/update/{classId}")
    public ResponseResult updateClass(@PathVariable("classId") String classId,@RequestBody Class class1) {
        return classService.updateClass(classId,class1);
    }

    @Override
    @PostMapping("/find/{page}")
    public QueryResponseResult<Class> findClassByFuzzyQuery(@PathVariable("page") int page,@RequestBody Class class1) {
        return classService.findClassByFuzzyQuery(page,class1);
    }

    @Override
    @GetMapping("/findall/{page}")
    public QueryResponseResult<Class> findAllClass(@PathVariable("page") int page) {
        return classService.findAllClass(page);
    }

    //查询班级学生
    @Override
    @GetMapping("/findclassstudent/{page}/{classId}")
    public QueryResponseResult<Student> findClassStudent(@PathVariable("page") int page,@PathVariable("classId") String classId) {
        return classService.findClassStudent(page,classId);
    }
    //查询班级课程
    @Override
    @GetMapping("/findclasscourse/{page}/{classId}")
    public QueryResponseResult<StudentCourse> findClassCourse(@PathVariable("page") int page,@PathVariable("classId") String classId) {
        return classService.findClassCourse(page,classId);
    }

    //查询班级老师
    @Override
    @GetMapping("/findclassteacher/{page}/{classId}")
    public QueryResponseResult<Teacher> findClassTeacher(@PathVariable("page") int page,@PathVariable("classId") String classId) {
        return classService.findClassTeacher(page,classId);
    }

}

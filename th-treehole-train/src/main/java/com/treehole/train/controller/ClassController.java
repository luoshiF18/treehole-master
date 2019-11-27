package com.treehole.train.controller;

import com.treehole.api.train.ClassControllerApi;
import com.treehole.framework.domain.train.*;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.ext.*;
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
    @PostMapping("/find/{page}/{size}")
    public QueryResponseResult<ClassHeadmaster> findClassByFuzzyQuery(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody Class class1) {
        return classService.findClassByFuzzyQuery(page,size,class1);
    }

    //查询班级学生
    @Override
    @PostMapping("/findclassstudent/{page}/{size}")
    public QueryResponseResult<Student> findClassStudent(@PathVariable("page") int page, @PathVariable("size") int size,@RequestBody StudentExt studentExt) {
        return classService.findClassStudent(page,size,studentExt);
    }
    //查询班级课程
    @Override
    @PostMapping("/findclasscourse/{page}/{size}")
    public QueryResponseResult<CourseTeacher> findClassCourse(@PathVariable("page") int page, @PathVariable("size") int size,@RequestBody CourseExt courseExt) {
        return classService.findClassCourse(page,size,courseExt);
    }

    //查询班级老师
    @Override
    @PostMapping("/findclassteacher/{page}/{size}")
    public QueryResponseResult<Teacher> findClassTeacher(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody  ClassTeacher classTeacher) {
        return classService.findClassTeacher(page,size,classTeacher);
    }

}

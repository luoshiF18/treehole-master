package com.treehole.train.controller;

import com.treehole.api.train.TeacherControllerApi;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.ClassHeadmaster;
import com.treehole.framework.domain.train.ext.TeacherCourseOfTeach;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/teacher")
public class TeacherController implements TeacherControllerApi {

    @Autowired
    TeacherService teacherService;

    @Override
    @PostMapping("/add")
    public ResponseResult addTeacher(@RequestBody  Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }

    @Override
    @DeleteMapping("delete/{teacherId}")
    public ResponseResult deleteTeacher(@PathVariable("teacherId") String teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }

    @Override
    @PutMapping("/update/{teacherId}")
    public ResponseResult updateTeacher(@PathVariable("teacherId") String teacherId,@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacherId,teacher);
    }

    @Override
    @PostMapping("/find/{page}/{size}")
    public QueryResponseResult<Teacher> findTeacherByFuzzyQuery(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody Teacher teacher) {
        return teacherService.findTeacherByFuzzyQuery(page,size,teacher);
    }

    @Override
    @PostMapping("/findTeacherClass/{page}/{size}")
    public QueryResponseResult<ClassHeadmaster> findTeacherClass(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody ClassHeadmaster classHeadmaster) {
        return teacherService.findTeacherClass(page, size, classHeadmaster);
    }

    @Override
    @PostMapping("/findTeacherCourseOfTeaching/{page}/{size}")
    public QueryResponseResult<TeacherCourseOfTeach> findTeacherCourseOfTeaching(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody TeacherCourseOfTeach teacherCourseOfTeach) {
        return teacherService.findTeacherCourseOfTeaching(page, size, teacherCourseOfTeach);
    }

    @Override
    @PostMapping("/findClassOfHeadTeacher/{page}/{size}")
    public QueryResponseResult<Class> findClassOfHeadTeacher(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody ClassHeadmaster classHeadmaster) {
        return teacherService.findClassOfHeadTeacher(page, size, classHeadmaster);
    }

}

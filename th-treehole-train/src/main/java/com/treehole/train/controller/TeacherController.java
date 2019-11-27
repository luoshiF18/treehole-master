package com.treehole.train.controller;

import com.treehole.api.train.TeacherControllerApi;
import com.treehole.framework.domain.train.Teacher;
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

}

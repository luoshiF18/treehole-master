package com.treehole.train.controller;

import com.treehole.api.train.ClassControllerApi;
import com.treehole.api.train.ClassCourseControllerApi;
import com.treehole.api.train.CostControllerApi;
import com.treehole.framework.domain.train.ClassCourse;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.ClassCourseService;
import com.treehole.train.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/classcourse")
public class ClassCourseController implements ClassCourseControllerApi {

    @Autowired
    ClassCourseService classCourseService;

    @Override
    @PostMapping("/add")
    public ResponseResult AddClassCourse(ClassCourse classCourse) {
        return classCourseService.AddClassCourse(classCourse);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseResult deleteClassCourse(String classId, String courseId) {
        return classCourseService.deleteClassCourse(classId,courseId);
    }

}

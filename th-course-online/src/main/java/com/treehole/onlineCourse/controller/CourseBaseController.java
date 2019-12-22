package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.CourseBaseControllerApi;
import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.service.CourseBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/course")
public class CourseBaseController implements CourseBaseControllerApi {

    @Autowired
    private CourseBaseService courseBaseService;

    //根据id查询课程基本信息
    @Override
    @GetMapping("/coursebase/findcoursebasebyid/{courseId}")
    public CourseBaseResult findCourseBaseById(@PathVariable("courseId") String courseId) {
        return courseBaseService.findCourseBaseById(courseId);
    }

    //更新课程基本信息，没有则添加
    @Override
    @PutMapping("/coursebase/updatecoursebase/{courseId}")
    public ResponseResult updateCourseBase(@PathVariable("courseId") String courseId,@RequestBody CourseBase courseBase) {
        return courseBaseService.updateCourseBase(courseId,courseBase);
    }

    //添加课程基本信息
    @Override
    @PostMapping("/coursebase/addcoursebase")
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseBaseService.addCourseBase(courseBase);
    }
}

package com.treehole.train.controller;

import com.treehole.api.train.CourseControllerApi;
import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;

    @Override
    @PostMapping("/add")
    public ResponseResult addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @Override
    @DeleteMapping("delete/{courseId}")
    public ResponseResult deleteCourse(@PathVariable("courseId") String courseId) {
        return courseService.deleteCourse(courseId);
    }

    @Override
    @PutMapping("/update/{courseId}")
    public ResponseResult updateCourse(@PathVariable("courseId") String courseId,@RequestBody Course course) {
        return courseService.updateCourse(courseId,course);
    }

    @Override
    @PostMapping("/find/{page}/{size}")
    public QueryResponseResult<CourseTeacher> findCourseByFuzzyQuery(@PathVariable("page") int page , @PathVariable("size") int size, @RequestBody Course course) {
        return courseService.findCourseByFuzzyQuery(page,size,course);
    }



}

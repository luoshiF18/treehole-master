package com.treehole.train.controller;

import com.treehole.api.train.ClassCourseControllerApi;
import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.ext.CourseList;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.SelectCourses;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.ClassCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train/classcourse")
public class ClassCourseController implements ClassCourseControllerApi {

    @Autowired
    ClassCourseService classCourseService;

    //选课
    @Override
    @PostMapping("/add/{classId}")
    public ResponseResult addClassCourse(@PathVariable("classId") String classId , @RequestBody CourseList courseList) {
        return classCourseService.addClassCourse(classId,courseList);
    }

    //退课
    @Override
    @DeleteMapping("/delete/{classId}/{courseId}")
    public ResponseResult deleteClassCourse(@PathVariable("classId") String classId ,@PathVariable("courseId") String courseId) {
        return classCourseService.deleteClassCourse(classId,courseId);
    }

    //查询需要进行选择的课程
    @Override
    @PostMapping("/find/{page}/{size}/{classId}")
    public QueryResponseResult<CourseTeacher> findCourseByFuzzyQuery(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("classId") String classId , @RequestBody SelectCourses selectCourses) {
        return classCourseService.findCourseToBeSelect(page,size,classId,selectCourses);
    }



}

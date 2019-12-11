package com.treehole.train.controller;

import com.treehole.api.train.ClassCourseControllerApi;
import com.treehole.framework.domain.train.ext.CourseList;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.SelectCourses;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.PhaseCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train/phasecourse")
public class PhaseCourseController implements ClassCourseControllerApi {

    @Autowired
    PhaseCourseService phaseCourseService;

    //选课
    @Override
    @PostMapping("/add/{phaseId}")
    public ResponseResult addClassCourse(@PathVariable("phaseId") String phaseId , @RequestBody CourseList courseList) {
        return phaseCourseService.addClassCourse(phaseId,courseList);
    }

    //退课
    @Override
    @DeleteMapping("/delete/{phaseId}/{courseId}")
    public ResponseResult deleteClassCourse(@PathVariable("phaseId") String phaseId ,@PathVariable("courseId") String courseId) {
        return phaseCourseService.deleteClassCourse(phaseId,courseId);
    }

    //查询需要进行选择的课程
    @Override
    @PostMapping("/find/{page}/{size}/{phaseId}")
    public QueryResponseResult<CourseTeacher> findCourseByFuzzyQuery(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("phaseId") String phaseId , @RequestBody SelectCourses selectCourses) {
        return phaseCourseService.findCourseToBeSelect(page,size,phaseId,selectCourses);
    }



}

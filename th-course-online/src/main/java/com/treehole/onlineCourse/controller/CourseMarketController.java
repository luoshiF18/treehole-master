package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.CourseBaseControllerApi;
import com.treehole.api.onlineCourse.CourseMarketControllerApi;
import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CourseMarket;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.CourseMarketResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.service.CourseBaseService;
import com.treehole.onlineCourse.service.CourseMarketService;
import com.treehole.onlineCourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/course")
public class CourseMarketController implements CourseMarketControllerApi {

    @Autowired
    private CourseMarketService courseMarketService;

    //根据id查询课程营销数据
    @Override
    @GetMapping("/coursemarket/findcoursemarketbyid/{courseId}")
    public CourseMarketResult findCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseMarketService.findCourseMarketById(courseId);
    }

    @Override
    @PutMapping("coursemarket/updatecoursemarket/{courseId}")
    public ResponseResult updateCourseMarket(@PathVariable("courseId") String courseId,@RequestBody CourseMarket courseMarket) {
        return courseMarketService.updateCourseMarket(courseId,courseMarket);
    }
}

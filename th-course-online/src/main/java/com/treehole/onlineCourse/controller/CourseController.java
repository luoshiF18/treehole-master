package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.CourseControllerApi;
import com.treehole.framework.domain.onlineCourse.TeachplanMedia;
import com.treehole.framework.domain.onlineCourse.ext.CourseView;
import com.treehole.framework.domain.onlineCourse.request.QueryPageRequest;
import com.treehole.framework.domain.onlineCourse.response.CoursePublishResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;

    //获取课程视图数据
    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView findCourseView(@PathVariable("courseId") String courseId) {
        return courseService.findCourseView(courseId);
    }

    //发布课程
    @Override
    @GetMapping("/publishcourse/{courseId}")
    public CoursePublishResult publishCourse(@PathVariable("courseId") String courseId) {
        return courseService.publishCourse(courseId);
    }

    //分页查询课程
    @Override
    @GetMapping("/findcourse/{page}/{size}")
    public QueryResponseResult findCourse(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return courseService.findCourse(page,size,queryPageRequest);
    }

    //关联课程计划与媒资信息
    @Override
    @PostMapping("/savemedia")
    public ResponseResult savemedia(@RequestBody TeachplanMedia teachplanMedia) {
        return courseService.savemedia(teachplanMedia);
    }

}

package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.CourseBaseControllerApi;
import com.treehole.api.onlineCourse.CourseSearchControllerApi;
import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CoursePub;
import com.treehole.framework.domain.onlineCourse.CourseSearchParam;
import com.treehole.framework.domain.onlineCourse.TeachplanMediaPub;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.service.CourseBaseService;
import com.treehole.onlineCourse.service.CourseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/search/course")
public class CourseSearchController implements CourseSearchControllerApi {

    @Autowired
    private CourseSearchService courseSearchService;

    //搜索课程
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CoursePub> searchCourse(@PathVariable("page") int page,@PathVariable("size") int size, CourseSearchParam courseSearchParam) {
        return courseSearchService.searchCourse(page,size,courseSearchParam);
    }

    //根据课程id查询所有课程信息
    @Override
    @GetMapping("/getall/{id}")
    public Map<String, CoursePub> getall(@PathVariable("id") String id) {
        return courseSearchService.getall(id);
    }

    @Override
    public TeachplanMediaPub getmedia(String teachplanId) {
        return null;
    }
}

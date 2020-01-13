package com.treehole.search.controller;

import com.treehole.api.onlineCourse.CourseSearchControllerApi;
import com.treehole.framework.domain.onlineCourse.CoursePub;
import com.treehole.framework.domain.onlineCourse.CourseSearchParam;
import com.treehole.framework.domain.onlineCourse.TeachplanMediaPub;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.search.service.CourseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //根据课程计划查询课程媒资信息
    @Override
    @GetMapping(value="/getmedia/{teachplanId}")
    public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId) {
        //将课程计划id放在数组中，为调用service作准备
        String[] teachplanIds = new String[]{teachplanId};
        //通过service查询ES获取课程媒资信息
        QueryResponseResult<TeachplanMediaPub> mediaPubQueryResponseResult = courseSearchService.getmedia(teachplanIds);
        QueryResult<TeachplanMediaPub> queryResult = mediaPubQueryResponseResult.getQueryResult();
        if(queryResult!=null
                && queryResult.getList()!=null
                && queryResult.getList().size()>0){
            //返回课程计划对应课程媒资
            return queryResult.getList().get(0);
        }
        return new TeachplanMediaPub();
    }
}

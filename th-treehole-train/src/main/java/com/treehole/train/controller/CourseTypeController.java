package com.treehole.train.controller;

import com.treehole.api.train.CourseTypeControllerApi;
import com.treehole.framework.domain.train.CourseType;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/coursetype")
public class CourseTypeController  implements CourseTypeControllerApi {

    @Autowired
    CourseTypeService courseTypeService;


    @Override
    @PostMapping("/add")
    public ResponseResult addCourseType(@RequestBody CourseType courseType) {
        return courseTypeService.addCourseType(courseType);
    }

    @Override
    @DeleteMapping("/delete/{courseTypeId}")
    public ResponseResult deleteCourseType(@PathVariable("courseTypeId") String courseTypeId) {
        return courseTypeService.deleteCourseType(courseTypeId);
    }

    @Override
    @PutMapping("/update/{courseTypeId}")
    public ResponseResult updateCourseType(@PathVariable("courseTypeId") String courseTypeId,@RequestBody CourseType courseType) {
        return courseTypeService.updateCourseType(courseTypeId,courseType);
    }

    @Override
    @PostMapping("/find/{page}/{size}")
    public QueryResponseResult<CourseType> findCourseTypeByFuzzyQuery(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody CourseType courseType) {
        return courseTypeService.findCourseTypeByFuzzyQuery(page,size,courseType);
    }
}

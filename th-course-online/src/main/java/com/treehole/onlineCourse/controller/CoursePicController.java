package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.CourseBaseControllerApi;
import com.treehole.api.onlineCourse.CoursePicControllerApi;
import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.CoursePicResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.service.CourseBaseService;
import com.treehole.onlineCourse.service.CoursePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/course")
public class CoursePicController implements CoursePicControllerApi {

    @Autowired
    private CoursePicService coursePicService;

    //文件上传
    @Override
    @PostMapping("/coursepic/uploadcoursepic")
    public CoursePicResult uploadCoursePic(MultipartFile multipartFile,String courseId) {
        return coursePicService.uploadCoursePic(multipartFile,courseId);
    }

    //查询文件
    @Override
    @GetMapping("/coursepic/findcoursepic/{courseId}")
    public CoursePicResult findCoursePic(@PathVariable("courseId") String courseId) {
        return coursePicService.findCoursePic(courseId);
    }

    @Override
    @DeleteMapping("/coursepic/deletecoursepic/{courseId}")
    public ResponseResult deleteCoursePic(@PathVariable("courseId") String courseId) {
        return coursePicService.deletecoursepic(courseId);
    }
}

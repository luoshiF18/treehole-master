package com.treehole.learning.controller;

import com.treehole.api.learning.CourseLearningControllerApi;
import com.treehole.framework.domain.onlineCourse.response.GetMediaResult;
import com.treehole.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cc
 * @date 2019/11/14 17:29
 */
@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {

    @Autowired
    private LearningService learningService;

    @Override
    @GetMapping("/getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getmedia(@PathVariable("courseId") String courseId, @PathVariable("teachplanId") String teachplanId) {
        return learningService.getMedia(courseId,teachplanId);
    }
}

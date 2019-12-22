package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.TeachplanControllerApi;
import com.treehole.framework.domain.onlineCourse.Teachplan;
import com.treehole.framework.domain.onlineCourse.ext.TeachplanNode;
import com.treehole.framework.domain.onlineCourse.response.TeachplanResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.service.TeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/course")
public class TeachplanController implements TeachplanControllerApi {

    @Autowired
    private TeachplanService teachplanService;

    //查询课程计划
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return teachplanService.findTeachplanList(courseId);
    }

    //添加课程计划
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return teachplanService.addTeachplan(teachplan);
    }

    //根据id查询课程计划
    @Override
    @GetMapping("/teachplan/getteachplanbyid/{teachplanId}")
    public TeachplanResult getTeachplanById(@PathVariable("teachplanId") String teachplanId) {
        return teachplanService.getTeachplanById(teachplanId);
    }

    //删除课程计划
    @Override
    @DeleteMapping("/teachplan/delteachplan/{teachplanId}")
    public ResponseResult delTeachplan(@PathVariable("teachplanId") String teachplanId) {
        return teachplanService.delTeachplan(teachplanId);
    }
}

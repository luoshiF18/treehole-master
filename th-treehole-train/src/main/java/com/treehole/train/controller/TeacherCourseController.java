package com.treehole.train.controller;
import com.treehole.api.train.TeacherCourseApi;
import com.treehole.framework.domain.train.ext.TeacherCourseShow;
import com.treehole.framework.domain.train.ext.TeacherSelectCourseList;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/teachercourse")
public class TeacherCourseController implements TeacherCourseApi {

    @Autowired
    TeacherCourseService teacherCourseService;

    //老师所选的课程
    @Override
    @PostMapping("/findTeacherCourse/{page}/{size}")
    public QueryResponseResult<TeacherCourseShow> findTeacherCourse(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody TeacherCourseShow teacherCourseShow) {
        return teacherCourseService.findTeacherCourse(page,size,teacherCourseShow);
    }

    @Override
    @PostMapping("/teacherCourseSelection/{teacherId}")
    public ResponseResult teacherCourseSelection(@PathVariable("teacherId")String teacherId,@RequestBody TeacherSelectCourseList teacherSelectCourseList) {
        return teacherCourseService.teacherCourseSelection(teacherId,teacherSelectCourseList);
    }

    @Override
    @PostMapping("/findExamineTeacherCourse/{page}/{size}")
    public QueryResponseResult<TeacherCourseShow> findExamineTeacherCourse(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody TeacherCourseShow teacherCourseShow) {
        return teacherCourseService.findExamineTeacherCourse(page, size, teacherCourseShow);
    }

    @Override
    @GetMapping("/examinePass/{id}")
    public ResponseResult examinePass(@PathVariable("id") String id) {
        return teacherCourseService.examinePass(id);
    }

    @Override
    @GetMapping("/examineNoPass/{id}")
    public ResponseResult examineNoPass(@PathVariable("id") String id) {
        return teacherCourseService.examineNoPass(id);
    }

    @Override
    @PostMapping("/findExamineSituation/{page}/{size}")
    public QueryResponseResult<TeacherCourseShow> findExamineSituation(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody TeacherCourseShow teacherCourseShow) {
        return teacherCourseService.findExamineSituation(page, size, teacherCourseShow);
    }
}

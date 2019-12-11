package com.treehole.train.dao;

import com.treehole.framework.domain.train.ext.TeacherCourseShow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TeacherCourseMapper {

    //查询老师选课的课程
    List<TeacherCourseShow> findTeacherCourse(TeacherCourseShow teacherCourseShow);

    //所审核的课程
    List<TeacherCourseShow> findExamineTeacherCourse(TeacherCourseShow teacherCourseShow);

    //查询审核情况
    List<TeacherCourseShow> findExamineSituation(TeacherCourseShow teacherCourseShow);


}

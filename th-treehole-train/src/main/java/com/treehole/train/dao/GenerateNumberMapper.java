package com.treehole.train.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GenerateNumberMapper {

    //查询学生中最新的id
    @Select("SELECT MAX(s.`student_id`)\n" +
            "FROM student s")
    String findStudentId();

    //查询老师中最新的id
    @Select("SELECT MAX(t.`teacher_id`)\n" +
            "FROM teacher t\n")
    String findTeacherId();

    //查询期数中最新的id
    @Select("SELECT MAX(p.`phase_id`)\n" +
            "FROM phase p\n")
    String findPhaseId();

    //查询课程类型中最新的id
    @Select("SELECT MAX(c.`course_type_id`)\n" +
            "FROM course_type c\n")
    String findCourseTypeId();

    //查询课程中最新的id
    @Select("SELECT MAX(c.`course_id`)\n" +
            "FROM course c\n")
    String findCourseId();

    //查询班级中最新的id
    @Select("SELECT MAX(c.`class_id`)\n" +
            "FROM class c\n")
    String findClassId();
}

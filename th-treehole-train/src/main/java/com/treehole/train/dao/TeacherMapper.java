package com.treehole.train.dao;

import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Teacher;

import com.treehole.framework.domain.train.ext.ClassHeadmaster;
import com.treehole.framework.domain.train.ext.TeacherCourseOfTeach;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TeacherMapper {

    //查询所有老师信息
     List<Teacher> findAllTeacher();
    //模糊所有老师信息
    List<Teacher> findTeacherByFuzzyQuery(Teacher teacher);

    //查询老师所带的课程
    @Select("SELECT COUNT(course_id)\n" +
            "FROM teacher t\n" +
            "LEFT JOIN course c\n" +
            "ON t.`teacher_id` = c.`course_teacher`\n" +
            "WHERE t.`teacher_id` = #{teacherId}")
    int findCourseByTeacherId(String teacherId);

    //查询老师所带的班级
    @Select("\n" +
            "SELECT COUNT(class_id)\n" +
            "FROM teacher t\n" +
            "LEFT JOIN class cl\n" +
            "ON t.`teacher_id` = cl.`class_headmaster`\n" +
            "WHERE t.`teacher_id` = #{teacherId}")
    int findClassByTeacherId(String teacherId);

    //查询老师所教的班级
    List<ClassHeadmaster> findTeacherClass(ClassHeadmaster classHeadmaster);

    //查询老师所教的课程
    List<TeacherCourseOfTeach>  findTeacherCourseOfTeaching(TeacherCourseOfTeach teacherCourseOfTeach);

    //查询班主任所带的班级
    List<Class>findClassOfHeadTeacher(ClassHeadmaster classHeadmaster);

}

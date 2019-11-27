package com.treehole.train.dao;

import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.SelectCourses;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ClassCourseMapper {

    //查询需要进行选择的课程
    List<CourseTeacher> findCourseToBeSelect(SelectCourses selectCourses);

    //查询所选课程的Id
    @Select("SELECT c.`course_id`\n" +
            "FROM class_course c\n" +
            "WHERE c.`class_id` = #{classId}")
    List<String> findSelectCourseId(String classId);

    //退课
    @Delete(" DELETE FROM class_course\n" +
            "WHERE class_id = #{classId} And course_id = #{courseId}\n ")
    void deleteClassCourse(String classId,String courseId);

}

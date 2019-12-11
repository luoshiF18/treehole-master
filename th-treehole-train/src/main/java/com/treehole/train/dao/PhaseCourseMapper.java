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
public interface PhaseCourseMapper {

    //查询需要进行选择的课程
    List<CourseTeacher> findCourseToBeSelect(SelectCourses selectCourses);

    //查询所选课程的Id
    @Select("SELECT c.`course_id`\n" +
            "FROM phase_course c\n" +
            "WHERE c.`phase_id` = #{phaseId}")
    List<String> findSelectCourseId(String phaseId);

    //退课
    @Delete(" DELETE FROM phase_course\n" +
            "WHERE phase_id = #{phaseId} And course_id = #{courseId}\n ")
    void deletePhaseCourse(String phaseId,String courseId);

}

package com.treehole.train.dao;

import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseMapper {

    //查询所有课程信息
    List<Course> findAllCourse();

    //模糊查询所有课程信息
    List<CourseTeacher> findCourseByFuzzyQuery(Course course);


}

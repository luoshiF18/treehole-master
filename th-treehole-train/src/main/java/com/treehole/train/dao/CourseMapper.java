package com.treehole.train.dao;

import com.treehole.framework.domain.train.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    //查询所有课程信息
    @Select(" select * from course")
    List<Course> findAllCourse();

    //模糊查询
    @Select("<script>\n" +
            "select * from course\n" +
            "        <where>\n" +
            "            <if test=\" courseId != null and courseId !=''\">\n" +
            "                and course_id = #{courseId}\n" +
            "            </if>\n" +
            "\t\t\t<if test=\" courseName != null and courseName !='' \">\n" +
            "                and course_name  like concat('%',#{courseName}, '%')\n" +
            "            </if>\n" +
            "\t\t\t<if test=\" courseType != null and courseType !='' \">\n" +
            "                and course_type = #{courseType}\n" +
            "            </if>\n" +
            "            <if test=\" courseBeginTime != null and courseBeginTime !='' \">\n" +
            "                and course_begin_time = #{courseBeginTime}\n" +
            "            </if>\n" +
            "            <if test=\" courseEndTime != null and courseEndTime !='' \">\n" +
            "                and course_end_time = #{courseEndTime}\n" +
            "            </if>\n" +
            "            <if test=\" courseTime != null and courseTime !='' \">\n" +
            "                and course_time = #{courseTime}\n" +
            "            </if>\n" +
            "            <if test=\" courseTeacher != null and courseTeacher !='' \">\n" +
            "                and course_teacher = #{courseTeacher}\n" +
            "            </if>\n" +
            "\t\t\t\n" +
            "        </where>\n" +
            "</script>")
    List<Course> findCourseByFuzzyQuery(Course course);




}

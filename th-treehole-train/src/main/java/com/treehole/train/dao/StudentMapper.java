package com.treehole.train.dao;

import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.StudentCourseParams;
import com.treehole.framework.domain.train.ext.StudentExt;
import com.treehole.framework.domain.train.ext.TeacherExt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface StudentMapper {



     //查询所有学生
     List<Student>  findAllStudent();

     //模糊查询学生
     List<StudentExt> findStudentByFuzzyQuery(Student student);

     //查询学生课程
     List<CourseTeacher> findStudentCourse(StudentCourseParams studentCourseParams);

     //查询学生老师
     List<Teacher> findStudentTeacher(TeacherExt teacherExt);

     //学生交费记录(欠费记录)
     List<Cost> findPay(Cost cost);

}

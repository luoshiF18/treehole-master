package com.treehole.train.dao;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface ClassMapper {

    //查询所有班级信息
    List<Class>  findAllClass();
    //模糊查询班级信息
    List<ClassHeadmaster> findClassByFuzzyQuery(Class class1);
    //查询班级学生
   List<Student> findClassStudent(StudentExt studentExt);

   //查询班机课程
   List<CourseTeacher> findClassCourse(CourseExt courseExt);
   //查新班级老师
   List<Teacher> findClassTeacher(ClassTeacher classTeacher);


}

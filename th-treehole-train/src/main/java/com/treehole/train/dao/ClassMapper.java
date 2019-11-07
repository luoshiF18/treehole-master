package com.treehole.train.dao;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
@Mapper
@Component
public interface ClassMapper {

    //查询所有班级信息
    List<Class>  findAllClass();
    //查询班级学生
   List<Student> findClassStudent(String classId);
   //查询班机课程
   List<StudentCourse> findClassCourse(String classId);
   //查新班级老师
   List<Teacher> findClassTeacher(String classId);


}

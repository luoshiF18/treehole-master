package com.treehole.train.dao;

import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.ext.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface StudentMapper {



     //查询所有学生
     List<Student>  findAllStudent();

     //查询学生课程
     List<StudentCourse> findStudentCourse(String id);

     //学生交费记录
     List<HashMap<String, Object>> findPay(String studentId);

     //学生欠费记录
     List<HashMap<String, Object>> findArrears(String studentId);
}

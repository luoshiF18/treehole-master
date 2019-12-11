package com.treehole.train.dao;

import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,String> {

    //查询目前班级未毕业的学生
    List<Student> findByStudentClassAndStudentGraduation(String classId , int studentGraduation);

    //根据期数查询学生
    List<Student> findByStudentPhase(String phaseId);
}

package com.treehole.train.dao;

import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,String> {

}

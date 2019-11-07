package com.treehole.train.dao;

import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,String> {




}

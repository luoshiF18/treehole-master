package com.treehole.train.dao;


import com.treehole.framework.domain.train.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,String> {


}

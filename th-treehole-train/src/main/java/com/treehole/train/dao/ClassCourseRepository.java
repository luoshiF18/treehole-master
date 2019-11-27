package com.treehole.train.dao;

import com.treehole.framework.domain.train.ClassCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassCourseRepository extends JpaRepository<ClassCourse,String> {

    //查询对应的id所选课程
    public Optional<ClassCourse> findByClassIdAndCourseId(String classId, String courseId);
    //根据courseId查看该课程有没有被选中
    public List<ClassCourse> findByCourseId(String courseId);

}

package com.treehole.train.dao;

import com.treehole.framework.domain.train.PhaseCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhaseCourseRepository extends JpaRepository<PhaseCourse,String> {

    //查询对应的id所选课程
    public Optional<PhaseCourse> findByPhaseIdAndCourseId(String phaseId, String courseId);
    //根据courseId查看该课程有没有被选中
    public List<PhaseCourse> findByCourseId(String courseId);

}

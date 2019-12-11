package com.treehole.train.dao;

import com.treehole.framework.domain.train.ClassCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassCourseRepository extends JpaRepository<ClassCourse,String> {

        //删除对应班级的课程
        void deleteByClassIdAndCourseId(String classId,String courseId);

}

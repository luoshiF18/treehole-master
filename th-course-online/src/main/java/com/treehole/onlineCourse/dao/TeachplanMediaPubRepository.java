package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cc
 * @date 2019/11/19 10:32
 */
public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub,String> {
    //根据课程id删除课程计划媒资信息
    long deleteByCourseId(String courseId);
}

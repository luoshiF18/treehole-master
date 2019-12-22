package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cc
 * @date 2019/10/24 15:18
 */
public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia,String> {
    //从teachplanmedia中查询课程计划媒资信息
    List<TeachplanMedia> findByCourseId(String courseId);
}

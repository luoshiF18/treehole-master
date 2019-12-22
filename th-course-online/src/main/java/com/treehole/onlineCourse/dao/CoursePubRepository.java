package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePubRepository extends JpaRepository<CoursePub,String> {

}

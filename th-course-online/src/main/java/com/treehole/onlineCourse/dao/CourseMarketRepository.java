package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cc
 * @date 2019/10/26 14:12
 */
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {
}

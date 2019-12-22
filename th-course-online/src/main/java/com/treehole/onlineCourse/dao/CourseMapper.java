package com.treehole.onlineCourse.dao;

import com.github.pagehelper.Page;
import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.ext.CourseInfo;
import com.treehole.framework.domain.onlineCourse.request.QueryPageRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   //根据id查询课程基本信息
   CourseBase findCourseBaseById(String id);
   //查询课程基本信息以及图片
   Page<CourseInfo> findCourseListPage(QueryPageRequest queryPageRequest);
}

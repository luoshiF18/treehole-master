package com.treehole.train.dao;

import com.treehole.framework.domain.train.CourseType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseTypeMapper {

    //模糊查询课程类型
    public List<CourseType> findCourseTypeByFuzzyQuery(CourseType courseType);



}

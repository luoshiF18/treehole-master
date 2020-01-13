package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cc
 * @date 2019/10/24 9:21
 */
@Mapper
public interface TeachplanMapper {
    //查询课程计划
    public TeachplanNode selectList(String courseId);
}

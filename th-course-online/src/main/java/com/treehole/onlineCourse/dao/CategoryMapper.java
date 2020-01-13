package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator.
 */
@Mapper
public interface CategoryMapper {
   //查询所有分类
   public List<CategoryNode> selectList();
}

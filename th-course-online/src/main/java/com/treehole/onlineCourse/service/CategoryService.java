package com.treehole.onlineCourse.service;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.ext.CategoryNode;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.onlineCourse.dao.CategoryMapper;
import com.treehole.onlineCourse.dao.CourseBaseRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //查询课程分类
    public List<CategoryNode> findCategoryList() {
        return categoryMapper.selectList();
    }
}

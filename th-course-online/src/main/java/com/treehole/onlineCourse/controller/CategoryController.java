package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.CategoryControllerApi;
import com.treehole.framework.domain.onlineCourse.ext.CategoryNode;
import com.treehole.onlineCourse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/course")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    private CategoryService categoryService;

    //查询课程分类
    @Override
    @GetMapping("/category/list")
    public List<CategoryNode> findCategoryList() {
        return categoryService.findCategoryList();
    }
}

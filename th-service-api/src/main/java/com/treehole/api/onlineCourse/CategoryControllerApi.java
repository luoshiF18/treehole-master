package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.ext.CategoryNode;
import com.treehole.framework.domain.onlineCourse.ext.CourseView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="在线课程接口管理",description = "提供在线课程课程分类操作")
public interface CategoryControllerApi {

   @ApiOperation("查询课程分类")
   public List<CategoryNode> findCategoryList();
}

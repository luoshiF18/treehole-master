package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.Dictionary;
import com.treehole.framework.domain.onlineCourse.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="在线课程接口管理",description = "提供在线课程数据字典操作")
public interface DictionaryControllerApi {

   @ApiOperation("查询数据字典")
   public List<Dictionary> findDictionaryByDType(String dType);
}

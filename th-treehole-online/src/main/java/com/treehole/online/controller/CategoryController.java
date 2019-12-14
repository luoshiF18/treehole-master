package com.treehole.online.controller;

import com.treehole.api.onlinetalk.CategoryControllerApi;
import com.treehole.framework.domain.onlinetalk.Category;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("online/category")
public class CategoryController implements CategoryControllerApi {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类信息
     * @param page
     * @param size
     * @param category
     * @return
     */
    @Override
    @GetMapping ("/getAllCategory")
    public QueryResponseResult getAllCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "5") int size,
                                              @RequestParam(value = "category", defaultValue = "")String category)  {
        QueryResult queryResult = categoryService.findAllCategory(page, size,category);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }


    /**
     * 根据id查询分类信息
     * @param id
     * @return
     */
    @Override
    @GetMapping("/find/id/{id}")
    public Category getCategoryById(@PathVariable("id") String id) {
         return categoryService.getCategoryById(id);
    }


    /**
     * 根据分类对象获取分类对象
     * @param category
     * @return
     */
    @GetMapping("/find")
    public Category getCategory(@RequestBody @Valid Category category){
        return  categoryService.findCategory(category);

    }


    /**
     * 根据分类id删除分类
     * @param category_id
     * @return
     */
    @Override
    @DeleteMapping(value ="/delete/id/{category_id}")
    public ResponseResult deleteCategoryById(@PathVariable("category_id") String category_id) {
         categoryService.deleteCategoryById(category_id);
        return new ResponseResult(CommonCode.SUCCESS);

    }


    /**
     * 新增分类
     * @param category
     * @return
     */
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertCategory(@RequestBody @Valid Category category) {


        categoryService.insertCategory(category);
        return new ResponseResult(CommonCode.SUCCESS);


    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateCategory(@RequestBody @Valid Category category){
        categoryService.updateCategory(category);

        return new ResponseResult(CommonCode.SUCCESS);
    }




}

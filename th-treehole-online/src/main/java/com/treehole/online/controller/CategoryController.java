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
 * @author shanhuijie
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("category")
public class CategoryController implements CategoryControllerApi {
    @Autowired
    private CategoryService categoryService;



    //http://localhost:40300/user/getAllUsers?page=3

    @Override
    @GetMapping ("/getAllCategory")
    public QueryResponseResult getAllCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "5") int size,
                                              @RequestParam(value = "category", defaultValue = "")String category)  {
        QueryResult queryResult = categoryService.findAllAgent(page, size,category);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }



    @Override
    @GetMapping("/find/id/{id}")
    public Category getCategoryById(@PathVariable("id") String id) {
         return categoryService.getCategoryById(id);
    }
    @GetMapping("/find")
    public Category getCategory(@RequestBody @Valid Category category){
        return  categoryService.findCategory(category);

    }

    /*@GetMapping("/find/")
    public User getUser*/

    @Override
    @DeleteMapping(value ="/delete/id/{category_id}")
    public ResponseResult deleteCategoryById(@PathVariable("category_id") String category_id) {
         categoryService.deleteCategoryById(category_id);
         //再判断用户是否存在
         /*if(this.getUserById(user_id) != null){
             ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
         }*/
        return new ResponseResult(CommonCode.SUCCESS);

    }



    @Override
    @PostMapping ("/insert")
    public ResponseResult insertCategory(@RequestBody @Valid Category category) {


        categoryService.insertCategory(category);
        return new ResponseResult(CommonCode.SUCCESS);


    }
    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    public ResponseResult updateCategory(@RequestBody @Valid Category category){
        //System.out.println("前端传来的+++++++++++++"+user);
        categoryService.updateCategory(category);

        return new ResponseResult(CommonCode.SUCCESS);
    }




}

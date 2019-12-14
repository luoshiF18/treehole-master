package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Category;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.CategoryMapper;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@Service
//为此类声明缓存名称
@Cacheable(value = "CategoryService")
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;



    /**
     * 查询所有快捷回复分类
     *
     * @param
     * @return List<User>
     */

    public QueryResult findAllCategory(Integer page, Integer size,String categoryname) {
        Page pag =PageHelper.startPage(page,size);
        List<Category> categories= new ArrayList<>();
        if (!categoryname.equals("") && categoryname!=null){
            Category category = new Category();
            category.setCategory_name(categoryname);
            //根据条件进行查询
            categories= categoryMapper.select(category);
            // replies = replyMapper.selectAll();
        }else {
            //查询全部
            categories = categoryMapper.selectAll();
        }

        if (CollectionUtils.isEmpty(categories)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Category> pageInfo = new PageInfo<>(categories);

        return new QueryResult(categories, pageInfo.getTotal());

    }

    /**
     * 根据category对象查询所有category记录
     *
     * @param
     * @return List<UserVo>
     */
    public Category findCategory(Category category) {
        Category category1 = categoryMapper.selectOne(category);
        if(category1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return category1;
    }




    /**
     * 通过id查询分类
     * @return category
     */
    public Category getCategoryById(String category_id){
       Category category = new Category();
       //
        category.setCategory_id(category_id);
        Category category1 = categoryMapper.selectOne(category);
        if(category1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return category1;
    }

    /**
     * 通过id删除分类
     * @param category_id
     * @return
     */
    @CacheEvict(value="CategoryService",allEntries=true)

    public void deleteCategoryById(String category_id) {

        if(StringUtils.isBlank(category_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        if(this.getCategoryById(category_id) == null){
            ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
        }
        Category category = new Category();
        category.setCategory_id(category_id);
        int del = categoryMapper.delete(category);

        if( del != 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }

    }

    /**
     * 创建一条分类信息
     *
     * @param category
     * @return int
     */
    @CacheEvict(value="CategoryService",allEntries=true)
    public void insertCategory(Category category)  {
        category.setCategory_id(MyNumberUtils.getUUID());
        category.setCategory_createtime(new Date());
        //
        int ins = categoryMapper.insert(category);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

    }

    /**
     * 更新分类基本信息
     *
     *
     * @param category
     * @return int
     */
    @CacheEvict(value="CategoryService",allEntries=true)
    public void updateCategory(Category category){

        Example example =new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("category_id",category.getCategory_id());
        int upd= categoryMapper.updateByExampleSelective(category,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }


}

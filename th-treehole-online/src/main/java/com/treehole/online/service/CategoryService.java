package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Category;
import com.treehole.framework.domain.onlinetalk.Reply;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.AgentMapper;
import com.treehole.online.mapper.CategoryMapper;
import com.treehole.online.myUtil.MyMd5Utils;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;



    /**
     * 查询所有用户
     *
     * @param
     * @return List<User>
     */

    public QueryResult findAllAgent(Integer page, Integer size,String categoryname) {
        Page pag =PageHelper.startPage(page,size);
        List<Category> categories= new ArrayList<>();
        if (!categoryname.equals("")){
            Category category = new Category();
            category.setCategory_name(categoryname);

            categories= categoryMapper.select(category);
            System.out.println("11111");
            // replies = replyMapper.selectAll();
        }else {
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
     * 通过id查询用户
     * @return List<User>
     */
    public Category getCategoryById(String category_id){
       Category category = new Category();
       //
        category.setCategory_id(category_id);
        Category category1 = categoryMapper.selectOne(category);
        if(category1 == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        return category1;
    }

    /**
     * 通过id删除分类
     * @param category_id
     * @return
     */
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
     * 创建一条用户信息
     *
     * @param category
     * @return int
     */
    public void insertCategory(Category category)  {
        category.setCategory_id(MyNumberUtils.getUUID());
        category.setCategory_createtime(new Date());
        //
        int ins = categoryMapper.insert(category);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        //往cards表中插入数据
       //cardsService.insertCard(user);
    }

    /**
     * 更新客服基本信息
     *
     *
     * @param category
     * @return int
     */
    public void updateCategory(Category category){

        Example example =new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("category_id",category.getCategory_id());
        //昵称
        /*String nickname = user.getUser_nickname();
        if(this.findUserByNickname(nickname) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/
        int upd= categoryMapper.updateByExampleSelective(category,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }

    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}

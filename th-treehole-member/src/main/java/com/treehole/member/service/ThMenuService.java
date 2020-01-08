package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.mapper.RoleMapper;
import com.treehole.member.mapper.ThMenuMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class ThMenuService {
    @Autowired
    private ThMenuMapper thMenuMapper;

    /**
     * 查询权限
     * @param page
     * @param size
     * @param
     * @return
     */
    public QueryResponseResult findAllMenu(Integer page,
                                           Integer size,
                                            String menu_name,
                                           String pid) {

        //判断请求条件的合法性

        Page pag = PageHelper.startPage(page,size);
        Example example = new Example(ThMenu.class);
        Example.Criteria criteria = example.createCriteria();
        //判断不为空字符串
        if(StringUtils.isNotEmpty(menu_name)){
            criteria.andLike("menuName", "%" + menu_name + "%");
        }
        if(StringUtils.isNotEmpty(pid)){
            //criteria.andLike("pId",   pid );
            criteria.andEqualTo("pid",  pid );
            //ThMenu thMenu = new ThMenu();
            //thMenu.setPId(pid);
           // criteria.equals(thMenu);
        }
        /*if(StringUtils.isNotEmpty(roleListRequest.getName())){
            criteria.andLike("role_name", "%" + roleListRequest.getName() + "%");
        }
        example.orderBy("create_time").desc();  //降序*/
        List<ThMenu> thMenus = thMenuMapper.selectByExample(example);
        //  解析分页结果
        PageInfo<PayGrade> pageInfo = new PageInfo<>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(thMenus);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }




    /*
     * 无条件查询所有用户等级信息
     * */

    public QueryResult findAll2(Integer pid){
        ThMenu thMenu = new ThMenu();
        thMenu.setPid(pid);
        List<ThMenu> thMenus = thMenuMapper.select(thMenu);
        if (CollectionUtils.isEmpty(thMenus)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<ThMenu> pageInfo = new PageInfo<>(thMenus);
        return new QueryResult(thMenus,pageInfo.getTotal());
    }
    /**
     * 查询父菜单信息
     */

    public QueryResult findAllf2(){
        ThMenu thMenu = new ThMenu();
        thMenu.setPid(0);
        List<ThMenu> thMenus = thMenuMapper.select(thMenu);
        if (CollectionUtils.isEmpty(thMenus)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<ThMenu> pageInfo = new PageInfo<>(thMenus);
        return new QueryResult(thMenus,pageInfo.getTotal());
    }








    /**
     * 根据menu对象查询menu
     * @param thMenu
     * @return
     */
    public ThMenu findMenuByMenu(ThMenu thMenu){

        return thMenuMapper.selectOne(thMenu);
    }

    /*增*/
    @Transactional
    public void insertThMenu(ThMenu thMenu) {
        if(thMenu == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        System.out.println("111"+thMenu);
        if(thMenu.getPid()==null){
            thMenu.setPid(0);
        }
        ThMenu thMenu1 = new ThMenu();
        thMenu1.setCode(thMenu.getCode());
        List<ThMenu> thMenus = thMenuMapper.select(thMenu1);
        if (thMenus.size()>0){
            ExceptionCast.cast(MemberCode.HAS_THIS_CODE);
        }
        //thMenu.setId(MyNumberUtils.getUUID());
        thMenu.setCreateTime(new Date());
        //role.setStatus("0"); //0正常
        int ins = thMenuMapper.insert(thMenu);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
    }

    /*更新*/
    @Transactional
    //@CacheEvict(value="MemberRole",allEntries=true)
    public void updateThMenu(ThMenu thMenu) {
        if(thMenu == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        thMenu.setCreateTime(new Date());
        Example example = new Example(ThMenu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",thMenu.getId());
        int ins = thMenuMapper.updateByExampleSelective(thMenu,example);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
    }

    /*删除*/
    @Transactional
    public void deleteThMenuById(Integer id) {
        //id不为空
        if(id == null){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //Role存在
        ThMenu thMenu = new ThMenu();
        thMenu.setId(id);
        if(this.findMenuByMenu(thMenu) != null){
            int del = thMenuMapper.delete(thMenu);
            if(del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
    }

    public ThMenu findThMenuById(Integer thMenu_id) {
        ThMenu thMenu = new ThMenu();
        thMenu.setId(thMenu_id);
        ThMenu menu = thMenuMapper.selectOne(thMenu);
        if(menu == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return menu;
    }



}

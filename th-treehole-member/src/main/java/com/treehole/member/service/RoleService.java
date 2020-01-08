package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.RoleMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /*
     * 根据rank,id,name查询所有付费会员等级信息
     * */
    //@Cacheable(value="MemberPayGrade")
    public QueryResponseResult findAll1(Integer page,
                                        Integer size,
                                        RoleListRequest roleListRequest) {

        //判断请求条件的合法性
        if (roleListRequest == null){
            roleListRequest = new RoleListRequest();
        }
        Page pag = PageHelper.startPage(page,size);
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        //判断不为空字符串
        if(StringUtils.isNotEmpty(roleListRequest.getId())){
            criteria.andLike("role_id", "%" + roleListRequest.getId() + "%");
        }
        if(StringUtils.isNotEmpty(roleListRequest.getName())){
            criteria.andLike("role_name", "%" + roleListRequest.getName() + "%");
        }
        example.orderBy("create_time").desc();  //降序
        List<Role> roles = roleMapper.selectByExample(example);
        //  解析分页结果
        PageInfo<PayGrade> pageInfo = new PageInfo<>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(roles);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /*
     * 无条件查询所有用户等级信息
     * */

    public QueryResult findAll2(){

        List<Role> roles = roleMapper.selectAll();
        if (CollectionUtils.isEmpty(roles)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return new QueryResult(roles,pageInfo.getTotal());
    }

    public Role findByName(String name){
        Role role = new Role();
        role.setRole_name(name);
        Role one = roleMapper.selectOne(role);
        return one;
    }

    public Role findRoleById(String role_id) {
        Role role = new Role();
        role.setRole_id(role_id);
        Role role1 = roleMapper.selectOne(role);
        if(role1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return role1;
    }

    /*查*/
    public Role findRoleByRole(Role role){
        return roleMapper.selectOne(role);
    }

    /*增*/
    @Transactional
    //@CacheEvict(value="MemberRole",allEntries=true)
    public void insertRole(Role role) {
        if(role == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        role.setRole_id(MyNumberUtils.getUUID());
        role.setCreate_time(new Date());
        role.setStatus(0); //0正常
        int ins = roleMapper.insert(role);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
    }

    /*更新*/
    @Transactional
    //@CacheEvict(value="MemberRole",allEntries=true)
    public void updateRole(Role role) {
        if(role == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        role.setUpdate_time(new Date());
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("role_id",role.getRole_id());
        int ins = roleMapper.updateByExampleSelective(role,example);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
    }

    /*删除*/
    @Transactional
    //@CacheEvict(value="MemberRole",allEntries=true)
    public void deleteRole(String id) {
        //id不为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //Role存在
        Role role = new Role();
        role.setRole_id(id);
        if(this.findRoleByRole(role) != null){
            int del = roleMapper.delete(role);
            if(del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
    }
}

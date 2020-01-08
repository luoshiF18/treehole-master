package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.Permission;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.Vo.PermissionVo;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.PermissionMapper;
import com.treehole.member.mapper.RoleMapper;
import com.treehole.member.mapper.ThMenuMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private  ThMenuMapper thMenuMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ThMenuService thMenuService;



    public List<PermissionVo> findPermissionByRoleId(String role_id){
        Permission permission = new Permission();
        permission.setRole_id(role_id);
        List<Permission> permissionList = permissionMapper.select(permission);
        List<PermissionVo> permissionVoList = new ArrayList<>();
        for (Permission permission1 : permissionList){
            PermissionVo permissionVo = new PermissionVo();
            permissionVo.setId(permission1.getId());
            permissionVo.setCreate_time(permission1.getCreate_time());
            permissionVo.setMenu_id(permission1.getMenu_id());
            permissionVo.setRole_id(permission1.getRole_id());
            permissionVo.setRole_name(roleService.findRoleById(permission1.getRole_id()).getRole_name());
            permissionVo.setMenu_name(thMenuService.findThMenuById(permission1.getMenu_id()).getMenuName());
            permissionVoList.add(permissionVo);
        }
        return  permissionVoList;

    }

    @Transactional
    public void deletePermissionById(String id) {
        //id不为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //Role存在
        Permission permission = new Permission();
        permission.setId(id);
        if(permissionMapper.selectOne(permission) != null){
            int del = permissionMapper.delete(permission);
            if(del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
    }

    @Transactional
    public void insertPermission(PermissionVo permissionVo) {
        //System.out.println("qqqqqqqqqq"+permissionVo);
        if(permissionVo == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        Permission permission1 = new Permission();
        permission1.setRole_id(permissionVo.getRole_id());
        List<Permission> list = permissionMapper.select(permission1);
        for(Permission permission2:list){
            if(permission2.getMenu_id().equals(permissionVo.getMenu_id())){
                ExceptionCast.cast(MemberCode.HAVA_THIS_MENU);
            }
        }

        if(permissionVo.getMenu_id().equals(-1)){
            ThMenu thMenu = new ThMenu();
            thMenu.setPid(permissionVo.getMenu_pid());
           // System.out.println("22222"+thMenu);
            List<ThMenu> thMenus = thMenuMapper.select(thMenu);
            for(ThMenu thMenu1 : thMenus){
                //判断是否存在
                if(this.findByRoleAndMenu(permissionVo.getRole_id(),thMenu1.getId() ).size()==0 ){
                    Permission permission = new Permission();
                    permission.setId(MyNumberUtils.getUUID());
                    permission.setRole_id(permissionVo.getRole_id());
                    permission.setMenu_id(thMenu1.getId());
                    permission.setCreate_time(new Date());
                    int ins = permissionMapper.insert(permission);
                    if(ins != 1){
                        ExceptionCast.cast(MemberCode.INSERT_FAIL);
                    }
                }

            }
        }
    }
    public  List<Permission>  findByRoleAndMenu(String role_id,Integer menu_id){
        Permission permission = new Permission();
        permission.setRole_id(role_id);
        permission.setMenu_id(menu_id);
       return permissionMapper.select(permission);
    }
}

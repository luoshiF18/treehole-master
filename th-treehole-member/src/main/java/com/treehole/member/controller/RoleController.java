package com.treehole.member.controller;

import com.treehole.api.member.FreeGradeControllerApi;
import com.treehole.api.member.RoleControllerApi;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.mapper.RoleMapper;
import com.treehole.member.service.FreegradeService;
import com.treehole.member.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.12.23 19:35
 */
@RestController
@RequestMapping("member/role")
public class RoleController implements RoleControllerApi {

    @Autowired
    private RoleService roleService;

    @Override
    @GetMapping("/find/all/{page}/{size}")
    @PreAuthorize("hasAuthority('member_role_find_all')")
    public QueryResponseResult findAllRole(@PathVariable("page") Integer page,
                                                @PathVariable("size") Integer size,
                                       RoleListRequest roleListRequest) {
        return  roleService.findAll1(page,size,roleListRequest);
    }



    @Override
    @GetMapping("/findAll")
    //@PreAuthorize("hasAuthority('member_role_find_nopage')")
    public QueryResponseResult findAll() {
        QueryResult result = roleService.findAll2();
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @Override
    @GetMapping("/find/name/{role_name}")
   // @PreAuthorize("hasAuthority('member_role_find_name')")
    public Role findByName(@PathVariable("role_name") String name) {
        return  roleService.findByName(name);
    }

    @Override
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('member_role_insert')")
    public ResponseResult insertRole(@RequestBody  @Valid Role role) {
       roleService.insertRole(role);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('member_role_delete')")
    public ResponseResult deleteRole(@PathVariable("id") String role_id) {
        roleService.deleteRole(role_id);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('member_role_update')")
    public ResponseResult updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}

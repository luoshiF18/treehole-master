package com.treehole.member.controller;

import com.treehole.api.member.PermissionControllerApi;
import com.treehole.api.member.ThMenuControllerApi;
import com.treehole.framework.domain.member.Permission;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.Vo.PermissionVo;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.PermissionService;
import com.treehole.member.service.ThMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Shan HuiJie
 * @Description: 角色——权限
 * @Date 2019.12.23 19:35
 */
@RestController
@RequestMapping("member/permission")
public class PerssionController implements PermissionControllerApi {

    @Autowired
    private PermissionService permissionService;


    @Override
    @GetMapping("/find/{role_id}")
    @PreAuthorize("hasAuthority('member_perssion_find_roleid')")
    public List<PermissionVo> findPermissionByRoleId(@PathVariable("role_id") String role_id) {
        return permissionService.findPermissionByRoleId(role_id);
    }

    /*@Override
    @GetMapping("/find/all")
    public QueryResponseResult findAllThMenu(@RequestParam(value = "page") Integer page,
                                             @RequestParam(value = "size") Integer size,
                                             @RequestParam(value = "menuName") String menu_name
                                             ) {
        return  thMenuService.findAllMenu(page,size,menu_name);
    }*/

   /* @Override
    @GetMapping("/find/menu/{id}")
    public ThMenu findThMenuById(@PathVariable("id") String thMenu_id) {
        return thMenuService.findThMenuById(thMenu_id);
    }

    @Override
    @GetMapping("/find/allMenuList")
    public List<ThMenu> findAllThMenu() {
        return  thMenuService.getAllMenuList();
    }

*/

    @Override
    @PostMapping("/insert")
    //@PreAuthorize("hasAuthority('member_perssion_insert')")
    public ResponseResult insertPermission(@RequestBody  @Valid PermissionVo permissionvo) {
        permissionService.insertPermission(permissionvo);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('member_perssion_delete')")
    public ResponseResult deletePermissionById(@PathVariable("id") String id) {
        permissionService.deletePermissionById(id);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    /*@Override
    @PutMapping("/update")
    public ResponseResult updateThMenu(@RequestBody @Valid ThMenu thMenu) {
        thMenuService.updateThMenu(thMenu);
        return new ResponseResult(CommonCode.SUCCESS);
    }*/


}

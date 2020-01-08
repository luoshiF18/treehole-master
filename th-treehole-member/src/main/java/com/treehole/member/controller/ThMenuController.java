package com.treehole.member.controller;

import com.treehole.api.member.RoleControllerApi;
import com.treehole.api.member.ThMenuControllerApi;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.RoleService;
import com.treehole.member.service.ThMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.12.23 19:35
 */
@RestController
@RequestMapping("member/ThMenu")
public class ThMenuController implements ThMenuControllerApi {

    @Autowired
    private ThMenuService thMenuService;

    @Override
    @GetMapping("/find/all")
    public QueryResponseResult findAllThMenu(@RequestParam(value = "page") Integer page,
                                             @RequestParam(value = "size") Integer size,
                                             @RequestParam(value = "menuName") String menu_name,
                                             @RequestParam(value = "menu_pid") String pid
                                             ) {
        return  thMenuService.findAllMenu(page,size,menu_name,pid);
    }

    @Override
    @GetMapping("/find/menu/{id}")
    public ThMenu findThMenuById(@PathVariable("id") Integer thMenu_id) {
        return thMenuService.findThMenuById(thMenu_id);
    }



    @Override
    @GetMapping("/findAll/{pid}")
    public QueryResponseResult findAllByPid(@PathVariable(value = "pid") Integer pid) {
        QueryResult result = thMenuService.findAll2(pid);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @Override
    @GetMapping("/findAllf")
    public QueryResponseResult findAllf() {
        QueryResult result = thMenuService.findAllf2();
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }


    @Override
    @PostMapping("/insert")
    public ResponseResult insertThMenu(@RequestBody  ThMenu thMenu) {
        //System.out.println("2222222222"+thMenu);
        thMenuService.insertThMenu(thMenu);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteThMenuById(@PathVariable("id") Integer id) {
        thMenuService.deleteThMenuById(id);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @PutMapping("/update")
    public ResponseResult updateThMenu(@RequestBody @Valid ThMenu thMenu) {
        thMenuService.updateThMenu(thMenu);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}

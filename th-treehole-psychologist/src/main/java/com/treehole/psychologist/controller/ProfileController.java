package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.ProfileControllerApi;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Helay
 * @date 2019/10/25 9:33
 */
@RestController
@RequestMapping("/psychologist/profile")
public class ProfileController implements ProfileControllerApi {

    @Autowired
    private ProfileService profileService;

    /**
     * 根据条件分页查询心理咨询师简介信息
     *
     * @param page          当前页码
     * @param size          每页记录数
     * @param name          心理咨询师姓名
     * @param sex           心理咨询师性别
     * @param qualification 心理咨询师资质
     * @return
     */
    @Override
    @GetMapping("/find/all")
    public QueryResponseResult findAllProfiles(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "qualification", required = false) String qualification
    ) {
        return this.profileService.findAllProfiles(page, size, name, sex, qualification);
    }

    /**
     * 根据id查询心理咨询师简介信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Override
    @GetMapping("/find/{id}")
    public Profile findProfileById(@PathVariable("id") String id) {
        return this.profileService.findProfileById(id);
    }

    /**
     * 根据id删除心理咨询师简介信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delProfileById(@PathVariable("id") String id) {
        return this.profileService.delProfileById(id);
    }

    /**
     * 添加心理咨询师简介信息
     *
     * @param profile 心理咨询师简介信息
     * @return
     */
    @Override
    @PostMapping("/add")
    public ResponseResult addProfile(@RequestBody Profile profile) {
        return this.profileService.addProfile(profile);
    }

    /**
     * 根据id更新心理咨询师简介信息
     *
     * @param profile 心理咨询师简介信息
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateProfile(@RequestBody Profile profile) {
        return this.profileService.updateProfile(profile);
    }

    /**
     * 按照id自增查询所有简介信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    @Override
    @GetMapping("get/all")
    public QueryResponseResult getAllProfiles(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        return this.profileService.getAllProfiles(page, size);
    }

}

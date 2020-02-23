package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.ProfileControllerApi;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.ext.DetailExt;
import com.treehole.framework.domain.psychologist.ext.ProfileExt;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 根据id删除心理咨询师所有信息，包括简介信息、状态信息，详情信息
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
     * 添加心理咨询师信息（包括简介、状态、详情）
     *
     * @param profileExt 心理咨询师信息扩展类
     * @return
     */
    @Override
    @PostMapping("/add")
    public ResponseResult addProfileExt(@RequestBody ProfileExt profileExt) {
        return this.profileService.addProfileExt(profileExt);
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
     * 根据地区和性别查询心理咨询师信息，门户展示使用
     *
     * @param page   当前页
     * @param size   每页记录数
     * @param region 咨询师所在地区
     * @param sex    咨询师性别
     * @return
     */
    @Override
    @GetMapping("/get")
    public QueryResponseResult findPsychologist(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "sex", required = false) String sex
    ) {
        return this.profileService.findPsychologist(page, size, region, sex);
    }

    /**
     * 根据咨询师id查询更多信息，门户展示使用
     *
     * @param id 咨询师id
     * @return
     */
    @Override
    @GetMapping("/get/detail/{id}")
    public DetailExt getPsychologistDetail(@PathVariable("id") String id) {
        return this.profileService.getPsychologistDetail(id);
    }

    /**
     * 获取咨询师资质占比情况
     *
     * @return
     */
    @Override
    @GetMapping("/get/qualificationCount")
    public Map<String, Object> getQualificationCount() {
        //查询出的数据格式为：{"name":"国家二级心理咨询师","value":3}，{"name":"国家三级心理咨询师","value":4}
        List<Map<String, Object>> qualificationCount = profileService.getQualificationCount();
        //构建map用来封装页面展示所需数据
        Map<String, Object> map = new HashMap<>();
        map.put("qualificationCount", qualificationCount);
        return map;
    }

    /**
     * 获取咨询师资质名
     *
     * @return
     */
    @Override
    @GetMapping("/get/qualificationNames")
    public Map<String, Object> getQualificationNames() {
        //查询咨询师资质占比情况
        List<Map<String, Object>> qualificationCount = this.profileService.getQualificationCount();
        //构建list集合用来存放咨询师资质名
        List<String> qualificationNames = new ArrayList<>();
        //构建map用来封装页面展示所需数据
        Map<String, Object> map = new HashMap<>();
        //遍历qualificationCount集合
        for (Map<String, Object> m : qualificationCount) {
            String name = (String) m.get("name");//取出资质名
            qualificationNames.add(name);//放入qualificationNames集合
            map.put("qualificationNames", qualificationNames);
        }
        return map;

    }
}

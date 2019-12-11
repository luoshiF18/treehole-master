package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.ConsultationControllerApi;
import com.treehole.framework.domain.psychologist.Consultation;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Helay
 * @date 2019/11/19 10:38
 */
@RestController
@RequestMapping("/psychologist/consultation")
public class ConsultationController implements ConsultationControllerApi {

    @Autowired
    private ConsultationService consultationService;

    /**
     * 根据用户id查询用户咨询记录列表
     *
     * @param page    当前页
     * @param size    每页记录数
     * @param user_id 用户id
     * @return
     */
    @Override
    @GetMapping("/get/list/user/{user_id}")
    public QueryResponseResult getConsultationsByUser(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @PathVariable("user_id") String user_id
    ) {
        return this.consultationService.getConsultationsByUser(page, size, user_id);
    }

    /**
     * 查询所有用户咨询记录
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    @Override
    @GetMapping("/get/all")
    public QueryResponseResult getAllConsultations(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        return this.consultationService.getAllConsultations(page, size);
    }

    /**
     * @param page            当前页
     * @param size            每页记录数
     * @param psychologist_id 咨询师id
     * @return
     */
    @Override
    @GetMapping("/get/list/psy/{psychologist_id}")
    public QueryResponseResult getConsultationsByPsy(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @PathVariable("psychologist_id") String psychologist_id
    ) {
        return this.consultationService.getConsultationsByPsy(page, size, psychologist_id);
    }

    /**
     * 根据咨询记录id删除咨询记录
     *
     * @param consultation_id 咨询记录id
     * @return
     */
    @Override
    @DeleteMapping("/del/{consultation_id}")
    public ResponseResult delConsultationById(@PathVariable("consultation_id") String consultation_id) {
        return this.consultationService.delConsultationById(consultation_id);
    }

    /**
     * 添加一条咨询记录
     *
     * @param consultation 咨询记录
     * @return
     */
    @Override
    @PostMapping("/add")
    public ResponseResult addConsultation(@RequestBody Consultation consultation) {
        return this.consultationService.addConsultation(consultation);
    }
}

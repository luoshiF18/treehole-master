package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.ConsultationControllerApi;
import com.treehole.framework.model.response.QueryResponseResult;
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
    @GetMapping("/get/list/{user_id}")
    public QueryResponseResult getConsultationListById(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @PathVariable("user_id") String user_id
    ) {
        return this.consultationService.getConsultationListById(page, size, user_id);
    }
}

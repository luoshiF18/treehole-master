package com.treehole.psychologist.api;

import com.treehole.framework.model.response.QueryResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Helay
 * @date 2019/11/19 15:52
 */
public interface ConsultationApi {

    /**
     * 根据用户id查询用户咨询记录列表
     *
     * @param page    当前页
     * @param size    每页记录书
     * @param user_id 用户id
     * @return
     */
    @GetMapping("/psychologist/consultation/get/list/{user_id}")
    QueryResponseResult getConsultationListById(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @PathVariable("user_id") String user_id
    );

}

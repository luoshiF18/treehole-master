package com.treehole.evaluation.api;

import com.treehole.framework.model.response.QueryResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther: Yan Hao
 * @date: 2019/10/21
 */
public interface ScaleSelectApi {
    /**
     * 根据量表名或用户id查询用户结果
     *
     * @param page
     * @param size
     * @param scaleName
     * @param userId
     * @return
     */
    @GetMapping("/scale/select/user/result")
    public QueryResponseResult findResult(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "scaleName", required = false) String scaleName,
            @RequestParam(value = "userId", required = false) String userId);
}

package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.ScaleManageControllerApi;
import com.treehole.evaluation.service.ScaleManageService;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 量表增删改查等
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@RestController
@RequestMapping("/scale/manage")
public class ScaleManageController implements ScaleManageControllerApi {
    @Autowired
    private ScaleManageService scaleManageService;

    /**
     * 添加新量表
     *
     * @return null
     */
    @Override
    @PostMapping("insert")
    public ResponseResult insertScale(@RequestBody Scale scale) {
        scaleManageService.insertScale(scale);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 搜索量表
     *
     * @param page   页数
     * @param size   每页多少条
     * @param sortBy 排序方式
     * @param key    搜索条件
     * @return
     */
    @Override
    @GetMapping("select")
    public QueryResponseResult findScale(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy,//排序方式
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,//是否降序
            @RequestParam(value = "key", required = false) String key
    ) {
        QueryResult queryResult = scaleManageService.findScale(page, size, sortBy, desc, key);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}

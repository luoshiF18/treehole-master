package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.ScaleDeleteControllerApi;
import com.treehole.evaluation.service.ScaleDeleteService;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@RestController
@RequestMapping("/scale/delete")
public class ScaleDeleteController implements ScaleDeleteControllerApi {

    @Autowired
    private ScaleDeleteService scaleDeleteService;

    /**
     * 删除量表
     *
     * @param scaleId
     * @return
     */
    @Override
    @DeleteMapping("scale")
    public ResponseResult deleteScale(@RequestParam("scaleId") String scaleId) {
        scaleDeleteService.deleteScale(scaleId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除某个用户的测评结果
     *
     * @param userId
     * @return
     */
    @Override
    @DeleteMapping("user/info")
    public ResponseResult deleteUserScale(@RequestParam("userId") String userId) {
        scaleDeleteService.deleteUserScale(userId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

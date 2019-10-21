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

    /**
     * 删除某个问题
     *
     * @param questionId
     * @return
     */
    @Override
    @DeleteMapping("question")
    public ResponseResult deleteQuestion(@RequestParam("questionId") String questionId) {
        scaleDeleteService.deleteQuestion(questionId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除某个选项
     *
     * @param optionId
     * @return
     */
    @Override
    @DeleteMapping("option")
    public ResponseResult deleteOption(@RequestParam("optionId") String optionId) {
        scaleDeleteService.deleteOption(optionId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除某个描述
     *
     * @param descId
     * @return
     */
    @Override
    @DeleteMapping("desc")
    public ResponseResult deleteDesc(@RequestParam("descId") String descId) {
        scaleDeleteService.deleteDesc(descId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除某个用户的某个结果
     *
     * @param resultId
     * @return
     */
    @Override
    public ResponseResult deleteResult(@RequestParam("resultId") String resultId) {
        scaleDeleteService.deleteResult(resultId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

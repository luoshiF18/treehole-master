package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.ScaleInsertControllerApi;
import com.treehole.evaluation.service.ScaleInsertService;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 量表增删改查等
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@RestController
@RequestMapping("/scale/insert")
public class ScaleInsertController implements ScaleInsertControllerApi {
    @Autowired
    private ScaleInsertService scaleInsertService;

    /**
     * 添加新量表
     *
     * @return null
     */
    @Override
    @PostMapping("scale")
    public ResponseResult insertScale(@RequestBody Scale scale) {
        scaleInsertService.insertScale(scale);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加问题和选项
     *
     * @return
     */
    @Override
    @PostMapping("question")
    public ResponseResult insertQuestionAndOption(@RequestBody QuestionDTO questionDTO) {
        scaleInsertService.insertQuestionAndOption(questionDTO);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加得分描述
     *
     * @param description
     * @return
     */
    @Override
    @PostMapping("description")
    public ResponseResult insertDescription(
             Description description) {
        return scaleInsertService.insertDescription(description);
    }




}

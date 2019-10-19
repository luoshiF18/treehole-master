package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.ScaleUpdateControllerApi;
import com.treehole.evaluation.service.ScaleUpdateService;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Result;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@RestController
@RequestMapping("/scale/update")
public class ScaleUpdateController implements ScaleUpdateControllerApi {
    @Autowired
    private ScaleUpdateService scaleUpdateService;

    /**
     * 更改量表信息
     *
     * @param scale
     * @return
     */
    @Override
    @PutMapping("Info")
    public ResponseResult updateScaleInfo(@RequestBody Scale scale) {
        scaleUpdateService.updateScaleInfo(scale);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更改量表问题和选项信息
     *
     * @param questionDTO
     * @return
     */
    @Override
    @PutMapping("question")
    public ResponseResult updateQuestion(@RequestBody QuestionDTO questionDTO) {
        scaleUpdateService.updateQuestion(questionDTO);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更改量表描述信息
     *
     * @param description
     * @return
     */
    @Override
    @PutMapping("desc")
    public ResponseResult updateDesc(@RequestBody Description description) {
        scaleUpdateService.updateDesc(description);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更改结果信息
     *
     * @param result
     * @return
     */
    @Override
    @PutMapping("result")
    public ResponseResult updateResult(@RequestBody Result result) {
        scaleUpdateService.updateResult(result);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

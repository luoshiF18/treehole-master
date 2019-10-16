package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.ScaleSelectControllerApi;
import com.treehole.evaluation.service.ScaleSelectService;
import com.treehole.framework.domain.evaluation.dto.OptionsDTO;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.response.ResultRequest;
import com.treehole.framework.domain.evaluation.response.StartTestResult;
import com.treehole.framework.domain.evaluation.vo.ResultVO;
import com.treehole.framework.domain.evaluation.vo.TestDetailVO;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@RestController
@RequestMapping("/scale/select")
public class ScaleSelectController implements ScaleSelectControllerApi {

    @Autowired
    private ScaleSelectService scaleSelectService;

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
    @GetMapping("scale")
    public QueryResponseResult findScale(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy,//排序方式
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,//是否降序,true是降序，false是升序
            @RequestParam(value = "key", required = false) String key
    ) {
        QueryResult queryResult = scaleSelectService.findScale(page, size, sortBy, desc, key);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 开始测试，获取量表内容
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("test")
    public StartTestResult startTest(@RequestParam(value = "scaleId", defaultValue = "") String scaleId) {
        TestDetailVO testDetailVO = scaleSelectService.startTest(scaleId);
        if (testDetailVO == null) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
        return new StartTestResult(CommonCode.SUCCESS, testDetailVO);
    }


    /**
     * 根据分数得出测试结果
     *
     * @param optionsDTO
     * @return
     */
    @Override
    @PostMapping("result")
    public ResultRequest testResult(@RequestBody OptionsDTO optionsDTO) {
        String userId = "null"; //TODO 需要获取用户id
        ResultVO testResult = scaleSelectService.getTestResult(optionsDTO, userId);
        if (testResult == null) {
            ExceptionCast.cast(EvaluationCode.TEST_ERROR);
        }
        return new ResultRequest(CommonCode.SUCCESS, testResult);
    }
}


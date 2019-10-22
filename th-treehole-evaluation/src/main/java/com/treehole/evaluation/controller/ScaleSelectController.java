package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.ScaleSelectControllerApi;
import com.treehole.evaluation.service.ScaleSelectService;
import com.treehole.framework.domain.evaluation.dto.OptionsDTO;
import com.treehole.framework.domain.evaluation.response.*;
import com.treehole.framework.domain.evaluation.vo.*;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @GetMapping("search")
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
     * 得到量表详细信息
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("detail")
    public DetailResult findScaleDetail(@RequestParam("scaleId") String scaleId) {
        ScaleDetailVO2 scaleDetail = scaleSelectService.findScaleDetail(scaleId);
        if (scaleDetail == null) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
        return new DetailResult(CommonCode.SUCCESS, scaleDetail);
    }

    /**
     * 搜索所有量表描述
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("desc")
    public QueryResponseResult findScaleDesc(String scaleId) {
        QueryResult scaleDesc = scaleSelectService.findScaleDesc(scaleId);
        return new QueryResponseResult(CommonCode.SUCCESS, scaleDesc);
    }

    /**
     * 开始测试，获取量表内容,简单类型
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("test/type1")
    public StartTestResult startTestType1(@RequestParam(value = "scaleId", defaultValue = "") String scaleId) {
//     TODO   只允许测试一次
        TestDetailVO testDetailVO = scaleSelectService.startTestType1(scaleId);
        if (testDetailVO == null) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
        return new StartTestResult(CommonCode.SUCCESS, testDetailVO);
    }

    /**
     * 开始测试,跳题类型
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("test/type2")
    public StartTestResult2 startTestType2(@RequestParam("scaleId") String scaleId,
                                           @RequestParam(value = "nextQuestionId", required = false) String nextQuestionId,
                                           @RequestParam(value = " questionSort", required = false) Integer questionSort,
                                           @RequestParam(value = " optionId", required = false) String optionId) {
//     TODO   只允许测试一次
        QuestionVO2 questionVO2 = scaleSelectService.startTestType2(scaleId, nextQuestionId, questionSort, optionId);
        if (questionVO2 == null) {
            ExceptionCast.cast(EvaluationCode.GET_QUESTION_ERROR);
        }
        return new StartTestResult2(CommonCode.SUCCESS, questionVO2);
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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = "null"; //TODO 需要获取用户id
        ResultVO testResult = scaleSelectService.getTestResult(optionsDTO, userId);
        if (testResult == null) {
            ExceptionCast.cast(EvaluationCode.TEST_ERROR);
        }
        return new ResultRequest(CommonCode.SUCCESS, testResult);
    }

    /**
     * 根据量表名和用户id查询用户选项
     *
     * @param scaleName
     * @param userId
     * @return
     */
    @Override
    @GetMapping("option")
    public UserOptionResult findUserOption(@RequestParam("scaleName") String scaleName, @RequestParam("userId") String userId) {
        UserOptionVO userOption = scaleSelectService.findUserOption(scaleName, userId);
        if (userOption == null) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
        return new UserOptionResult(CommonCode.SUCCESS, userOption);
    }

    /**
     * 根据量表名或用户id查询用户结果
     *
     * @param scaleName
     * @param userId
     * @return
     */
    @Override
    @GetMapping("user/result")
    public QueryResponseResult findResult(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "scaleName", required = false) String scaleName,
            @RequestParam(value = "userId", required = false) String userId) {
        QueryResult<ResultVO> result = scaleSelectService.findResult(page, size, scaleName, userId);
        return new QueryResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 获取要更改的问题和选项
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("up/question")
    public UpdateQuestionResult findUpdateQuestion(@RequestParam("scaleId") String scaleId) {
        List<QuestionVO3> updateQuestions = scaleSelectService.findUpdateQuestion(scaleId);
        if (updateQuestions == null) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
        return new UpdateQuestionResult(CommonCode.SUCCESS, updateQuestions);
    }
}


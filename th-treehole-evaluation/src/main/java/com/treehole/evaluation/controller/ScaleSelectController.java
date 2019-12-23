package com.treehole.evaluation.controller;

import com.alibaba.fastjson.JSON;
import com.treehole.api.evaluation.ScaleSelectControllerApi;
import com.treehole.evaluation.MyUtils.MyCookieUtils;
import com.treehole.evaluation.client.UserClient;
import com.treehole.evaluation.dao.ResultMapper;
import com.treehole.evaluation.dao.ScaleMapper;
import com.treehole.evaluation.service.ScaleSelectService;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Result;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.dto.OptionsDTO;
import com.treehole.framework.domain.evaluation.response.*;
import com.treehole.framework.domain.evaluation.vo.*;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.utils.Oauth2Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@RestController
@RequestMapping("/scale/select")
public class ScaleSelectController implements ScaleSelectControllerApi {

    @Autowired
    private ScaleSelectService scaleSelectService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ScaleMapper scaleMapper;
    @Autowired
    private ResultMapper resultMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

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
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "typeId", required = false) String typeId
    ) {
        QueryResult queryResult = scaleSelectService.findScale(page, size, sortBy, desc, key, typeId);
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
    public DetailResult findScaleDetail(@RequestParam(value = "scaleId", required = false) String scaleId,
                                        @RequestParam(value = "scaleName", required = false) String scaleName) {
        ScaleDetailVO2 scaleDetail = scaleSelectService.findScaleDetail(scaleId, scaleName);
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
     * 开始测试，获取量表内容,多选类型
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("test/type1")
    public StartTestResult startTestType1(@RequestParam(value = "scaleId") String scaleId,
                                          @RequestParam(value = "nextQuestionSort", required = false) Integer nextQuestionSort) {
//        只允许测试一次
        //      只允许测试一次
        if (nextQuestionSort == null) {
            if (!checkResult(scaleId)) {
                ExceptionCast.cast(EvaluationCode.HAVE_DONE);
            }
        }
        QuestionVO questionVO = scaleSelectService.startTestType1(scaleId, nextQuestionSort);
        if (questionVO == null) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
        return new StartTestResult(CommonCode.SUCCESS, questionVO);
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
                                           @RequestParam(value = "questionSort", required = false) Integer questionSort,
                                           @RequestParam(value = "optionId", required = false) String optionId) {
//      只允许测试一次
        if (StringUtils.isEmpty(nextQuestionId)) {
            if (!checkResult(scaleId)) {
                ExceptionCast.cast(EvaluationCode.HAVE_DONE);
            }
        }
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
        String userId = getUserId();
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

    /**
     * 获取所有分类
     *
     * @return
     */
    @Override
    @GetMapping("type/all")
    public QueryResponseResult findScaleType() {
        QueryResult scaleType = scaleSelectService.findScaleType();
        return new QueryResponseResult(CommonCode.SUCCESS, scaleType);
    }


    /**
     * 获取所有分数计算方法
     *
     * @return
     */
    @Override
    @GetMapping("score/method")
    public QueryResponseResult findScoreMethod() {
        QueryResult allScoreMethod = scaleSelectService.findAllScoreMethod();
        return new QueryResponseResult(CommonCode.SUCCESS, allScoreMethod);
    }

    /**
     * 根据分类id查询量表
     *
     * @param page
     * @param size
     * @param scaleTypeId
     * @return
     */
    @Override
    @GetMapping("type")
    public QueryResponseResult findScaleByType(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "scaleTypeId") String scaleTypeId,
            @RequestParam(value = "isFree", defaultValue = "true") Boolean isFree) {
        QueryResult queryResult = scaleSelectService.findScaleByType(page, size, scaleTypeId, isFree);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据量表id查询下一个要添加的问题id
     *
     * @param scaleId
     * @return
     */
    @Override
    @GetMapping("/next/sort")
    public Integer findNextQuestionSort(@RequestParam("scaleId") String scaleId) {
        return scaleSelectService.findNextQuestionSort(scaleId);
    }

    /**
     * 获取一个问题的信息
     *
     * @param questionId
     * @return
     */
    @Override
    @GetMapping("/find/question")
    public UpdateOneQuestionResult findOneQuestion(@RequestParam("questionId") String questionId) {
        QuestionVO3 question = scaleSelectService.findOneQuestion(questionId);
        return new UpdateOneQuestionResult(CommonCode.SUCCESS, question);
    }

    /**
     * 获取一个得分描述的信息
     *
     * @param descId
     * @return
     */
    @Override
    @GetMapping("/find/one/desc")
    public Description findOneDescription(@RequestParam("descId") String descId) {
        return scaleSelectService.findOneDescription(descId);
    }

    /**
     * 校验是否做过该题
     *
     * @return
     */
    private boolean checkResult(String scaleId) {
        String userId = getUserId();
        if (userId == null) {
            return true;
        }
        Scale scale = scaleMapper.selectByPrimaryKey(scaleId);
//        如果用户登录了就校验
        Result result = new Result();
        result.setScaleName(scale.getScaleName());
        result.setUserId(userId);
        Result selectOne = resultMapper.selectOne(result);
        if (selectOne == null) {
            return true;
        } else {
//            如果做过就不让继续做了
            return false;
        }
    }

    /**
     * 获取用户Id
     */
    private String getUserId() {
        //        获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //      获取信息
        String cookieValue = MyCookieUtils.getCookieValue(request, "uid");
        String token = redisTemplate.opsForValue().get("user_token:" + cookieValue);
        String between = StringUtils.substringBetween(token, "\"jwt_token\":\"", "\",");
        Jwt decode = JwtHelper.decode(between);
        //得到 jwt中的用户信息
        String claims = decode.getClaims();
        //将jwt转为Map
        Map<String, String> map = null;
        map = JSON.parseObject(claims, Map.class);
        String id = map.get("id");
        return id;
    }

}


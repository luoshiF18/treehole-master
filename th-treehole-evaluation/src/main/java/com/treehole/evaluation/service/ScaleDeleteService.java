package com.treehole.evaluation.service;

import com.treehole.evaluation.dao.*;
import com.treehole.framework.domain.evaluation.*;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther: Yan Hao
 * @date: 2019/10/18
 */
@Service
public class ScaleDeleteService {

    @Autowired
    private ScaleMapper scaleMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private DescriptionMapper descriptionMapper;
    @Autowired
    private ResultMapper resultMapper;
    @Autowired
    private UserOptionMapper userOptionMapper;

    /**
     * 删除量表
     *
     * @param scaleId
     * @return
     */
    @Transactional
    public void deleteScale(String scaleId) {
//        不废话，就是一顿删
        try {
            if (StringUtils.isNotBlank(scaleId)) {
                Question question = new Question();
                question.setScaleId(scaleId);
                Option option = new Option();
                option.setScaleId(scaleId);
                Description description = new Description();
                description.setScaleId(scaleId);
                questionMapper.delete(question);
                optionMapper.delete(option);
                descriptionMapper.delete(description);
                scaleMapper.deleteByPrimaryKey(scaleId);
                //TODO 删除预警信息
            }
        } catch (Exception e) {
            ExceptionCast.cast(EvaluationCode.DELETE_ERROR);
            e.printStackTrace();
        }

    }

    /**
     * 删除某个用户的测评结果
     *
     * @param userId
     * @return
     */
    @Transactional
    public void deleteUserScale(String userId) {
//        不废话，删起来
        try {
            if (StringUtils.isNotBlank(userId)) {
                Result result = new Result();
                result.setUserId(userId);
                UserOption userOption = new UserOption();
                userOption.setUserId(userId);
                userOptionMapper.delete(userOption);
                resultMapper.delete(result);
                //TODO 删除预警信息
            }
        } catch (Exception e) {
            ExceptionCast.cast(EvaluationCode.DELETE_ERROR);
            e.printStackTrace();
        }


    }

    /**
     * 删除某个问题
     *
     * @param questionId
     * @return
     */
    @Transactional
    public void deleteQuestion(String questionId) {
//        不废话，删起来
        try {
            if (StringUtils.isNotBlank(questionId)) {
                Option option = new Option();
                option.setQuestionId(questionId);
                optionMapper.delete(option);
                questionMapper.deleteByPrimaryKey(questionId);
            }
        } catch (Exception e) {
            ExceptionCast.cast(EvaluationCode.DELETE_ERROR);
            e.printStackTrace();
        }
    }

    /**
     * 删除某个选项
     *
     * @param optionId
     * @return
     */
    @Transactional
    public void deleteOption(String optionId) {
//        不废话，删起来
        try {
            if (StringUtils.isNotBlank(optionId)) {
                optionMapper.deleteByPrimaryKey(optionId);
            }
        } catch (Exception e) {
            ExceptionCast.cast(EvaluationCode.DELETE_ERROR);
            e.printStackTrace();
        }
    }

    /**
     * 删除某个描述
     *
     * @param descId
     * @return
     */
    @Transactional
    public void deleteDesc(String descId) {
        //        不废话，删起来
        try {
            if (StringUtils.isNotBlank(descId)) {
                descriptionMapper.deleteByPrimaryKey(descId);
            }
        } catch (Exception e) {
            ExceptionCast.cast(EvaluationCode.DELETE_ERROR);
            e.printStackTrace();
        }

    }

    /**
     * 删除某个用户的某个结果
     *
     * @param resultId
     * @return
     */
    @Transactional
    public void deleteResult(String resultId) {
//        不废话，删起来
        try {
            if (StringUtils.isNotBlank(resultId)) {
//                先查询用户的量表名称和id
                Result result = resultMapper.selectByPrimaryKey(resultId);
                UserOption userOption = new UserOption();
                userOption.setScaleName(result.getScaleName());
                userOption.setUserId(result.getUserId());
//                删除用户该量表的选项
                userOptionMapper.delete(userOption);
                resultMapper.deleteByPrimaryKey(resultId);
            }
        } catch (Exception e) {
            ExceptionCast.cast(EvaluationCode.DELETE_ERROR);
            e.printStackTrace();
        }
    }
}

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
                scaleMapper.deleteByPrimaryKey(scaleId);
                questionMapper.delete(question);
                optionMapper.delete(option);
                descriptionMapper.delete(description);
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
                resultMapper.delete(result);
                userOptionMapper.delete(userOption);
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
}

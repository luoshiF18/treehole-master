package com.treehole.evaluation.service;

import com.treehole.evaluation.MyUtils.MyChineseCharUtil;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.*;
import com.treehole.framework.domain.evaluation.*;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Service
public class ScaleInsertService {

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

    /**
     * 添加新量表
     *
     * @return null
     */
    public void insertScale(Scale scale) {
//        如果数据为空报错
        if (scale == null) {
            ExceptionCast.cast(EvaluationCode.SCALE_DATA_NULL);
        }
//        存入欠缺数据
        scale.setId(MyNumberUtils.getUUID());
        String upperCase = MyChineseCharUtil.getUpperCase(scale.getScaleName(), false);
        scale.setLetter(upperCase);
        scale.setCreateTime(new Date());
        scale.setCreateUserId(scale.getCreateUserId()); //TODO userId想办法获取
        if (scaleMapper.insert(scale) != 1) {
            ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
        }
    }


    /**
     * 添加问题和选项
     *
     * @param questionDTO
     */
    @Transactional
    public void insertQuestionAndOption(QuestionDTO questionDTO) {
//        判断前台数据
        if (questionDTO == null) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
//        存入欠缺数据
        Question question = new Question();
        String uuid = MyNumberUtils.getUUID();
        question.setId(uuid);
        question.setScaleId(questionDTO.getScaleId());
        question.setSort(questionDTO.getSort());
        question.setQuestion(questionDTO.getQuestion());
        question.setCreateTime(new Date());
        question.setCreateUserId(questionDTO.getUpdateUserId());//TODO userId想办法获取
        question.setRemark(questionDTO.getRemark());
//        判断
        if (questionMapper.insert(question) != 1) {
            ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
        }
//        设置选项列表，增加添加效率,不能使用tk.mybatis的insertList<>，因为主键是string，不是自增
//        List<Option> optionList = new ArrayList<>();
//        存储选项
        List<Option> options = questionDTO.getOptions();
        for (Option option : options) {
            String id = MyNumberUtils.getUUID();
            String questionId = uuid;
            Integer sort = option.getSort();
            String answer = option.getAnswer();
            Integer score = option.getScore();
            Integer skip = option.getScore();
            Integer skipId = option.getSkipId();
            if (optionMapper.insertList(id, questionId, sort, answer, score, skip, skipId) != 1) {
                ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
            }
        }
    }

    /**
     * 添加得分描述
     *
     * @param scaleId
     * @param score1
     * @param score2
     * @param description
     * @param warningLevel
     * @return
     */
    public void insertDescription(String scaleId, Integer score1, Integer score2, String description, Integer warningLevel, String createUserId) {
        if (StringUtils.isEmpty(scaleId) || StringUtils.isEmpty(description) || score1 < 0 || score2 < 0 || score1 > score2 || warningLevel < 0) {
            ExceptionCast.cast(EvaluationCode.DESC_DATA_ERROR);
        }
        Description descriptionInsert = new Description();
        descriptionInsert.setId(MyNumberUtils.getUUID());
        descriptionInsert.setScaleId(scaleId);
        descriptionInsert.setDescription(description);
        descriptionInsert.setScore1(score1);
        descriptionInsert.setScore2(score2);
        descriptionInsert.setWarningLevel(warningLevel);
        descriptionInsert.setCreateTime(new Date());
        descriptionInsert.setCreateUserId("暂时么得cookie");//TODO userId想办法获取
        if (descriptionMapper.insert(descriptionInsert) != 1) {
            ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
        }
    }

    /**
     * 添加测评结果
     *
     * @return
     */
    public void insertResult(String userId, String scaleId, String descriptionId, Integer score, String warningId) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(scaleId) || StringUtils.isEmpty(descriptionId) || StringUtils.isEmpty(warningId) || score < 0) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        Result result = new Result();
//        存入欠缺数据
        result.setId(MyNumberUtils.getUUID());
        result.setUserId(userId);
        result.setScaleId(scaleId);
        result.setDescriptionId(descriptionId);
        result.setScore(score);
        result.setCreateTime(new Date());
        result.setWarningId(warningId);
        if (resultMapper.insert(result) != 1) {
            ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
        }
    }
}

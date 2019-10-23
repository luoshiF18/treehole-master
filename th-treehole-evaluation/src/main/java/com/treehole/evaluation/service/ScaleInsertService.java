package com.treehole.evaluation.service;

import com.treehole.evaluation.MyUtils.MyChineseCharUtil;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.*;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Option;
import com.treehole.framework.domain.evaluation.Question;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
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
    @Transactional
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
            Float score = option.getScore();
            Integer skip = option.getSkip();
            String skipId = option.getSkipId();
            String scaleId = option.getScaleId();
            if (optionMapper.insertList(id, questionId, sort, answer, score, skip, skipId, scaleId) != 1) {
                ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
            }
        }
    }

    /**
     * 添加得分描述
     *
     * @param description
     * @return
     */
    @Transactional
    public ResponseResult insertDescription(Description description) {

        if (description == null) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        try {
            description.setId(MyNumberUtils.getUUID());
            description.setScaleId(description.getId());
            description.setCreateUserId("3"); //从cookie中拿
            description.setCreateTime(new Date());
            descriptionMapper.insert(description);
            return ResponseResult.SUCCESS();
        } catch (RuntimeException e) {
            return new ResponseResult(EvaluationCode.DESCRIPTION_IS_ERROR);
        }

    }

    /*    *//**
     * 添加测评结果
     *
     *//*
    public void insertResult(String userId, String scaleId, String descriptionId, Float score, String warningId) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(scaleId) || StringUtils.isEmpty(descriptionId) || StringUtils.isEmpty(warningId) || score < 0) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        Result result = new Result();
//        存入欠缺数据
        result.setId(MyNumberUtils.getUUID());
        result.setUserId(userId);
//        result.setScaleId(scaleId);
//        result.setDescriptionId(descriptionId);
        result.setScore(score);
        result.setCreateTime(new Date());
        result.setWarningInfo(warningId);
        if (resultMapper.insert(result) != 1) {
            ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
        }
    }*/
}

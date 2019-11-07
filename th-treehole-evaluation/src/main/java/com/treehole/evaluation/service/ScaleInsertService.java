package com.treehole.evaluation.service;

import com.treehole.evaluation.MyUtils.MyChineseCharUtil;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.*;
import com.treehole.framework.domain.evaluation.*;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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
    @Autowired
    private ScaleTypeMapper scaleTypeMapper;

    /**
     * 添加新量表
     *
     * @return null
     */
    @Transactional
    public void insertScale(Scale scale) {

//        校验数据
        if (scaleTypeMapper.selectByPrimaryKey(scale.getTypeId()) == null
                || StringUtils.isBlank(scale.getScaleName())
                || StringUtils.isBlank(scale.getScaleName())
                || StringUtils.isBlank(scale.getTypeId())
                || StringUtils.isBlank(scale.getTopicDescription())
                || StringUtils.isBlank(scale.getTopicBackground())
                || StringUtils.isBlank(scale.getTopicSuggest())
                || StringUtils.isBlank(scale.getScaleFunction())
                || StringUtils.isBlank(scale.getGuide())
        ) {
            ExceptionCast.cast(EvaluationCode.SCALE_DATA_NULL);
        }
//        校验名称
        if (StringUtils.isNotEmpty(scaleMapper.findScaleIdByName(scale.getScaleName()))) {
            ExceptionCast.cast(EvaluationCode.SCALE_NAME_SAME);
        }
//        存入欠缺数据
        scale.setId(MyNumberUtils.getUUID());
        String upperCase = MyChineseCharUtil.getUpperCase(scale.getScaleName(), false);
        scale.setLetter(upperCase);
        scale.setCreateTime(new Date());
        scale.setCreateUserId(scale.getCreateUserId()); //TODO userId想办法获取
        if (scale.getPrice() == null || scale.getPrice().compareTo(BigDecimal.ZERO) == -1) {
            scale.setPrice(BigDecimal.valueOf(0.00));
        }
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
        if (StringUtils.isBlank(questionDTO.getQuestion())
                || (questionDTO.getSort() < 1)
                || (questionDTO.getSort() == null)
                || StringUtils.isBlank(questionDTO.getScaleId())
        ) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
//       判断序号是否存在
        Question question1 = new Question();
        question1.setScaleId(questionDTO.getScaleId());
        question1.setSort(questionDTO.getSort());
        if (questionMapper.selectOne(question1) != null) {
            ExceptionCast.cast(EvaluationCode.SORT_ERROR);
        }
        Question question = new Question();
        String uuid = MyNumberUtils.getUUID();
        question.setId(uuid);
        question.setScaleId(questionDTO.getScaleId());
        question.setSort(questionDTO.getSort());
        question.setQuestion(questionDTO.getQuestion());
/*        question.setCreateTime(new Date());
        question.setCreateUserId(questionDTO.getUpdateUserId());
        question.setRemark(questionDTO.getRemark());*/
//        判断
        if (questionMapper.insert(question) != 1) {
            ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
        }
//        设置选项列表，增加添加效率,不能使用tk.mybatis的insertList<>，因为主键是string，不是自增
//        List<Option> optionList = new ArrayList<>();
//        存储选项
        List<Option> options = questionDTO.getOptions();
        for (Option option : options) {
//            判断选项
            if (StringUtils.isEmpty(option.getAnswer()) || option.getScore() == null || option.getScore() < 0) {
                ExceptionCast.cast(EvaluationCode.DATA_ERROR);
            }
//            如果有跳题
            if ((option.getSkip() == 1 && StringUtils.isBlank(option.getSkipId()))
                    || (StringUtils.isNotEmpty(option.getSkipId()) && option.getSkip() == 0)) {
                ExceptionCast.cast(EvaluationCode.DATA_ERROR);
            }
            String id = MyNumberUtils.getUUID();
            String questionId = uuid;
            Integer sort = option.getSort();
            String answer = option.getAnswer();
            Float score = option.getScore();
            Integer skip = option.getSkip();
            String skipId = option.getSkipId();
            String scaleId = questionDTO.getScaleId();
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
        if (StringUtils.isEmpty(description.getDescription())
                || StringUtils.isEmpty(description.getScaleId())
                || description.getWarningLevel() == null
                || description.getWarningLevel() < 0
        ) {
            ExceptionCast.cast(EvaluationCode.DATA_MISS);
        }
//        校验分数
        Float score1 = description.getScore1();
        Float score2 = description.getScore2();
//       1 初步校验
        if (score1 < 0
                || score1 == null
                || score2 == null
                || score1 > score2) {
            ExceptionCast.cast(EvaluationCode.SCORE_ERROR);
        }

//        2 数据库校验
        Description description1 = new Description();
        description1.setScaleId(description.getScaleId());
        List<Description> allDesc = descriptionMapper.select(description1);
//        如果设置有分数段，校验
        if (!CollectionUtils.isEmpty(allDesc)) {
            for (Description descForCheck : allDesc) {
//            判断新低分数是否在前面设置的分数段中
                if ((score1 < descForCheck.getScore2() && descForCheck.getScore1() < score1)
                        || (Math.abs(score1 - descForCheck.getScore1()) <= 0)
                        || (Math.abs(score2 - descForCheck.getScore1()) <= 0)
                        || (Math.abs(score1 - descForCheck.getScore2()) <= 0)
                        || (Math.abs(score2 - descForCheck.getScore2()) <= 0)
                ) {
                    ExceptionCast.cast(EvaluationCode.SCORE_ERROR);
                    break;
                }
            }
        }
//        如果没事儿了，终于可以存了，累死了
        try {
            description.setId(MyNumberUtils.getUUID());
            description.setScaleId(description.getScaleId());
/*            description.setCreateUserId("3"); //从cookie中拿
            description.setCreateTime(new Date());*/
            descriptionMapper.insert(description);
            return ResponseResult.SUCCESS();
        } catch (RuntimeException e) {
            return new ResponseResult(EvaluationCode.DESCRIPTION_IS_ERROR);
        }
    }

    /**
     * 添加量表分类
     *
     * @param type
     * @return
     */
    @Transactional
    public void insertScaleType(String type) {
        if (StringUtils.isEmpty(type)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
//        如果数据库有这条就别存了啊，大哥
        ScaleType scaleType2 = new ScaleType();
        scaleType2.setScaleType(type);
        if (scaleTypeMapper.selectOne(scaleType2) != null) {
            ExceptionCast.cast(EvaluationCode.TYPE_HAD);
        }
//        没有你就存
        ScaleType scaleType = new ScaleType();
        scaleType.setId(MyNumberUtils.getUUID());
        scaleType.setScaleType(type);
        if (scaleTypeMapper.insert(scaleType) != 1) {
            ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
        }
    }

    /**
     * 处理跳题，把问题序号转为问题id
     *
     * @param scaleId
     * @return
     */
    @Transactional
    public void handleOptionSkip(String scaleId) {
        if (StringUtils.isEmpty(scaleId)) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
//        查出所有选项，处理
        Option option = new Option();
        option.setScaleId(scaleId);
        List<Option> options = optionMapper.select(option);
        for (Option option1 : options) {
//            判断是否符合条件
            if (option1.getSkip() == 1 && option1.getSkipId() != null && option1.getSkipId().length() < 4) {
//                这时 option1.getSkipId() 是问题序号
                String questionId = questionMapper.findQuestionIdBySortAndScaleId(scaleId, option1.getSkipId());
                if (questionId == null) {
                    ExceptionCast.cast(EvaluationCode.SKIP_TO_QiD_ERROR);
                }
                option1.setSkipId(questionId);
//                存入
                if (optionMapper.updateByPrimaryKeySelective(option1) != 1) {
                    ExceptionCast.cast(EvaluationCode.INSERT_FAIL);
                }
            }
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

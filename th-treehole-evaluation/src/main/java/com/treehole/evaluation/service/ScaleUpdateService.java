package com.treehole.evaluation.service;

import com.treehole.evaluation.MyUtils.MyChineseCharUtil;
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
 * @date: 2019/10/18
 */
@Service
public class ScaleUpdateService {
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
     * 更改量表信息
     *
     * @param scale
     * @return
     */
    @Transactional
    public void updateScaleInfo(Scale scale) {
//        如果名字更改，更改对应的letter
        if (!StringUtils.equals(scale.getScaleName(), scaleMapper.selectByPrimaryKey(scale.getId()).getScaleName())) {
            String upperCase = MyChineseCharUtil.getUpperCase(scale.getScaleName(), false);
            scale.setLetter(upperCase);
        }
//        设置更新时间
        scale.setUpdateTime(new Date());
        scale.setUpdateUserId(null); //TODO 获取用户id
        if (scaleMapper.updateByPrimaryKeySelective(scale) != 1) {
            ExceptionCast.cast(EvaluationCode.UPDATE_ERROR);
        }
    }

    /**
     * 更改量表问题和选项信息
     *
     * @param questionDTO
     * @return
     */
    @Transactional
    public void updateQuestion(QuestionDTO questionDTO) {
//        存入问题表
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setQuestion(questionDTO.getQuestion());
        question.setSort(questionDTO.getSort());
        question.setRemark(questionDTO.getRemark());
        question.setUpdateTime(new Date());
        question.setUpdateUserId(null); //  TODO 获取用户id
//        判断是否成功
        if (questionMapper.updateByPrimaryKeySelective(question) != 1) {
            ExceptionCast.cast(EvaluationCode.UPDATE_ERROR);
        }
//        处理选项
        List<Option> options = questionDTO.getOptions();
        for (Option option : options) {
            //        判断是否成功
            if (optionMapper.updateByPrimaryKeySelective(option) != 1) {
                ExceptionCast.cast(EvaluationCode.UPDATE_ERROR);
            }
        }
    }

    /**
     * 更改量表描述信息
     *
     * @param description
     * @return
     */
    @Transactional
    public void updateDesc(Description description) {
        description.setUpdateTime(new Date());
        description.setUpdateUserId(null); //TODO 获取用户id
        if (descriptionMapper.updateByPrimaryKeySelective(description) != 1) {
            ExceptionCast.cast(EvaluationCode.UPDATE_ERROR);
        }
    }

    /**
     * 更改结果信息
     *
     * @param result
     * @return
     */
    @Transactional
    public void updateResult(Result result) {
//        如果更改过咨询师预警信息或者备注，添加更改人的信息
        if (StringUtils.isNotBlank(result.getUserWarningInfo()) || StringUtils.isNotBlank(result.getRemark())) {
            result.setUpdateUserId("暂时没有"); //TODO 获取更改人的用户id
            result.setUpdateTime(new Date());
        }
        if (resultMapper.updateByPrimaryKeySelective(result) != 1) {
            ExceptionCast.cast(EvaluationCode.UPDATE_ERROR);
        }
    }
}

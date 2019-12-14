package com.treehole.evaluation.service;

import com.treehole.evaluation.MyUtils.MyChineseCharUtil;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.*;
import com.treehole.framework.domain.evaluation.*;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.utils.Oauth2Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Scale scaleOld = scaleMapper.selectByPrimaryKey(scale.getId());
        if (!StringUtils.equals(scale.getScaleName(), scaleOld.getScaleName())) {
            String upperCase = MyChineseCharUtil.getUpperCase(scale.getScaleName(), false);
            scale.setLetter(upperCase);
        }
//        如果金额更改并且小于0
        if (scale.getPrice().compareTo(BigDecimal.ZERO) == -1) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
//        设置更新时间
        scale.setUpdateTime(new Date());
        scale.setUpdateUserId(getUserId());
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
/*        question.setRemark(questionDTO.getRemark());
        question.setUpdateTime(new Date());
        question.setUpdateUserId(null); // */
//        判断是否成功
        if (questionMapper.updateByPrimaryKeySelective(question) != 1) {
            ExceptionCast.cast(EvaluationCode.UPDATE_ERROR);
        }
//        处理选项
        List<Option> options = questionDTO.getOptions();
        for (Option option : options) {
            if (StringUtils.isEmpty(option.getId())) {
                option.setId(MyNumberUtils.getUUID());
                option.setQuestionId(questionDTO.getId());
                option.setScaleId(questionDTO.getScaleId());
                optionMapper.insert(option);
                break;
            }
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
//        从新增那里搬来的验证，应该能用，nice!!
        if (StringUtils.isEmpty(description.getDescription())
                || StringUtils.isEmpty(description.getScaleId())
                || description.getWarningLevel() == null
                || description.getWarningLevel() < 0
        ) {
            ExceptionCast.cast(EvaluationCode.DATA_MISS);
        }
//       验证没事就更新
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
            result.setUpdateUserId(getUserId());
            result.setUpdateTime(new Date());
        }
        if (resultMapper.updateByPrimaryKeySelective(result) != 1) {
            ExceptionCast.cast(EvaluationCode.UPDATE_ERROR);
        }
    }

    /**
     * 获取用户Id
     */
    private String getUserId() {
        //        获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //      获取信息
        Map<String, String> userInfo = Oauth2Util.getJwtClaimsFromHeader(request);
        return userInfo.get("id");
    }
}

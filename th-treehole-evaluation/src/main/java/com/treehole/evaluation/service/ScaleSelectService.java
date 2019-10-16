package com.treehole.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.MyUtils.MyDateUtils;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.*;
import com.treehole.framework.domain.evaluation.*;
import com.treehole.framework.domain.evaluation.dto.OptionsDTO;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.OptionVO;
import com.treehole.framework.domain.evaluation.vo.QuestionVO;
import com.treehole.framework.domain.evaluation.vo.ResultVO;
import com.treehole.framework.domain.evaluation.vo.TestDetailVO;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@Service
public class ScaleSelectService {

    @Autowired
    private ScaleMapper scaleMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private DescriptionMapper descriptionMapper;
    @Autowired
    private UserOptionMapper userOptionMapper;
    @Autowired
    private ResultMapper resultMapper;

    /**
     * 搜索量表
     *
     * @param page   页数
     * @param size   每页多少条
     * @param sortBy 排序方式
     * @param key    搜索条件
     * @return
     */
    public QueryResult findScale(Integer page, Integer size, String sortBy, Boolean desc, String key) {
//        分页
        PageHelper.startPage(page, size);
//        过滤
        /**
         * where 'scaleName' like "%x%" or letter=='x'
         * order by shortName desc
         */
//        把字节码传给example，就可以通过反射获取数据库信息
        Example example = new Example(Scale.class);
        if (StringUtils.isNotBlank(key)) {
            //过滤条件
            example.createCriteria().orLike("scaleName", "%" + key + "%")
                    .orEqualTo("shortName", key.toUpperCase()/*这里是把它变成大写，因为数据库里是大写*/);
        }
//         排序
    /*    if (StringUtils.isNotBlank(sortBy)) {
            if (sortBy.equals("DESC")) {
                example.setOrderByClause("type_id DESC");
            } else {
                example.setOrderByClause("type_id ASC");
            }
        }*/
        //排序
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + " " + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }
//        查询
        List<Scale> scales = scaleMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(scales)) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
//        解析分页结果
        PageInfo<Scale> pageInfo = new PageInfo<>(scales);

        return new QueryResult(scales, pageInfo.getTotal());
    }

    /**
     * 开始测试，获取量表内容
     *
     * @param scaleId
     * @return
     */
    public TestDetailVO startTest(String scaleId) {
        if (StringUtils.isEmpty(scaleId)) {
            ExceptionCast.cast(EvaluationCode.SCALE_FIND_ERROR);
        }
        try {
//        展示数据
            TestDetailVO testDetailVO = new TestDetailVO();
//        获取量表信息
            Scale scale = scaleMapper.selectByPrimaryKey(scaleId);
//        存入展示数据中
            testDetailVO.setId(scaleId);
            testDetailVO.setScaleName(scale.getScaleName());
            testDetailVO.setShortName(scale.getShortName());
            testDetailVO.setTopicDescription(scale.getTopicDescription());
            testDetailVO.setTopicSuggest(scale.getTopicSuggest());
            testDetailVO.setTopicBackground(scale.getTopicBackground());
            testDetailVO.setGuide(scale.getGuide());
            testDetailVO.setScaleFunction(scale.getScaleFunction());
            testDetailVO.setCreateTime(MyDateUtils.dateToString2(scale.getCreateTime(), "yyyy-MM-dd"));
            testDetailVO.setImages(scale.getImages());
            testDetailVO.setType(scale.getTypeId()); //TODO 这里需要搜索详细类型名称

//        获取量表问题
            Question question = new Question();
            question.setScaleId(scaleId);
            List<Question> questions = questionMapper.select(question);
//        问题展示集合
            List<QuestionVO> questionVOList = new ArrayList<>();
//        循环遍历出问题，存入问题展示类
            for (Question question1 : questions) {
//            每个都存入问题展示类
                QuestionVO questionVO = new QuestionVO();
                String question1Id = question1.getId();
                questionVO.setId(question1Id);
                questionVO.setSort(question1.getSort());
                questionVO.setQuestion(question1.getQuestion());
//            获取该问题的选项
                Option option = new Option();
                option.setQuestionId(question1Id);
//            获取该问题选项的集合
                List<Option> options = optionMapper.select(option);
                List<OptionVO> optionVOS = new ArrayList<>();
//            获取量表问题里的选项
                for (Option option1 : options) {
                    OptionVO optionVO = new OptionVO();
                    optionVO.setId(option1.getId());
                    optionVO.setSort(option1.getSort());
                    optionVO.setAnswer(option1.getAnswer());
                    optionVOS.add(optionVO);
                }
                questionVO.setOptionVOS(optionVOS);
                questionVOList.add(questionVO);
            }
//        把问题们存入显示类中
            testDetailVO.setQuestionVOS(questionVOList);
//        返回
            return testDetailVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据分数得出测试结果
     *
     * @param optionsDTO
     * @return
     */
    @Transactional
    public ResultVO getTestResult(OptionsDTO optionsDTO, String userId) {
        try {
//        量表id
            String scaleId = optionsDTO.getScaleId();
//        获取量表对象
            Scale scale = scaleMapper.selectByPrimaryKey(scaleId);
//        得出总分数
            List<String> optionIds = optionsDTO.getOptionIds();
//        选项信息
            StringBuilder questionAndOption = new StringBuilder();
            System.out.println(questionAndOption);
            int sum = 0;
            if (StringUtils.isNotBlank(userId)) {
//            得出分数并存储选项
                for (String optionId : optionIds) {
                    Option UserOption = optionMapper.getOptionList1(optionId);
                    Integer integer = UserOption.getScore();
                    sum += integer;
//                拼接字符串
                    questionAndOption.append(UserOption.getQuestionId() + "|" + UserOption.getAnswer() + ",");
                }
//            存入到选项表
                insertUserOption(userId, scaleId, questionAndOption.toString());
            } else {
//            如果没有登录就不记录，直接返回分数
                for (String optionId : optionIds) {
                    Integer integer = optionMapper.getScore(optionId);
                    sum += integer;
                }
            }

//        获取量表描述
            Description description = getDescription(scaleId, sum);
//        如果警告等级超过2级预警
            if (description.getWarningLevel() >= 2) {
                // TODO 获取预警信息
            }
//        准备展示数据
            ResultVO resultVO = new ResultVO();
            resultVO.setScaleName(scale.getScaleName());
            resultVO.setDescriptionInfo(description.getDescription());
            resultVO.setScore(sum);
            if (StringUtils.isNotBlank(userId)) {
                resultVO.setUserName(userId); //TODO 存入用户名称
                insertResult(userId, scaleId, description.getId(), sum, null);
            }
            resultVO.setWarningInfo(null);
            resultVO.setWarningLevel(description.getWarningLevel());
//        返回
            return resultVO;
        } catch (Exception e) {
//            如果出现遗产返回空
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取匹配的量表描述
     */
    private Description getDescription(String scaleId, Integer sum) {
//        查出量表的描述信息
        Description description = new Description();
        description.setScaleId(scaleId);
        List<Description> descriptions = descriptionMapper.select(description);
//        匹配相应的量表

        for (Description desc : descriptions) {
            if (sum >= desc.getScore1() && sum <= desc.getScore2()) {
                description = desc;
                break;
            }
        }
        return description;
    }

    /**
     * 存入到选项表里
     */
    private void insertUserOption(String userId, String scaleId, String questionAndOption) {
//        准备数据
        UserOption userOption = new UserOption();
        userOption.setId(MyNumberUtils.getUUID());
        userOption.setUserId(userId);
        userOption.setScaleId(scaleId);
        userOption.setQuestionAndOption(questionAndOption);
        userOption.setTime(new Date());
//        存入
        userOptionMapper.insert(userOption);
    }

    /**
     * 存入到结果表中
     */
    private void insertResult(String userId, String scaleId, String descriptionId, Integer score, String warningId) {
//        准备数据
        Result result = new Result();
        result.setId(MyNumberUtils.getUUID());
        result.setUserId(userId);
        result.setScaleId(scaleId);
        result.setDescriptionId(descriptionId);
        result.setScore(score);
        result.setCreateTime(new Date());
        result.setWarningId(warningId);
//         存入
        resultMapper.insert(result);
    }


}

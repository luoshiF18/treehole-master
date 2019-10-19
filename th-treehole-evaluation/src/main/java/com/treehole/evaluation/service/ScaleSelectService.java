package com.treehole.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.MyUtils.MyDateUtils;
import com.treehole.evaluation.MyUtils.MyMapUtils;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.*;
import com.treehole.framework.domain.evaluation.*;
import com.treehole.framework.domain.evaluation.dto.OptionsDTO;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.*;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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

//    private static final String USER_OPTIONS = "user:options";

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
     * 得到量表详细信息
     *
     * @param scaleId
     * @return
     */
    public ScaleDetailVO findScaleDetail(String scaleId) {
        if (StringUtils.isBlank(scaleId)) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        try {
//            查询量表
            Scale scale = scaleMapper.selectByPrimaryKey(scaleId);
//            填入展示类
            ScaleDetailVO scaleDetailVO = new ScaleDetailVO();
            scaleDetailVO.setId(scaleId);
            scaleDetailVO.setScaleName(scale.getScaleName());
            scaleDetailVO.setShortName(scale.getShortName());
            scaleDetailVO.setTopicDescription(scale.getTopicDescription());
            scaleDetailVO.setTopicSuggest(scale.getTopicSuggest());
            scaleDetailVO.setTopicBackground(scale.getTopicBackground());
            scaleDetailVO.setGuide(scale.getGuide());
            scaleDetailVO.setScaleFunction(scale.getScaleFunction());
            scaleDetailVO.setCreateTime(MyDateUtils.dateToString2(scale.getCreateTime(), "yyyy-MM-dd"));
            scaleDetailVO.setImages(scale.getImages());
            scaleDetailVO.setType("暂时没有");//TODO 这里需要搜索详细类型名称
            scaleDetailVO.setScaleType(scale.getScaleType());
//            返回
            return scaleDetailVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 开始测试，获取量表内容,简单类型
     *
     * @param scaleId
     * @return
     */
    public TestDetailVO startTestType1(String scaleId) {
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
     * 开始测试,跳题类型
     *
     * @return
     */
    public QuestionVO2 startTestType2(String scaleId, String nextQuestionId, Integer questionSort, String optionId) {
        try {
            if (StringUtils.isNotBlank(nextQuestionId) || questionSort != null) {
//        第二次查询带有nextQuestionId
//            获取问题
                StringBuilder nextId = new StringBuilder();
                StringBuilder question = new StringBuilder();
                Integer sort = 0;
//            如果有跳题处理
                Option option = optionMapper.getOptionList1(optionId);
                String skipId = option.getSkipId();
                Integer skip = option.getSkip();
//                如果有跳转id，并且跳转状态为开启就跳转
                if (skip == 1 && skipId != null) {
                    nextId.append(skipId);
                    Question select = questionMapper.selectByPrimaryKey(skipId);
                    sort = select.getSort();
                    question.append(select.getQuestion());

                } else {
//            如果没有跳题处理
                    Question questionInfo = questionMapper.findQuestionInfo(questionSort + 1, scaleId);
                    nextId.append(questionInfo.getId());
                    sort = questionSort + 1;
                    question.append(questionInfo.getQuestion());
                }
//            准备返回数据
                QuestionVO2 questionVO2 = new QuestionVO2();
                questionVO2.setNextQuestionId(nextId.toString());
                questionVO2.setQuestionSort(sort);
                questionVO2.setQuestion(question.toString());
                questionVO2.setOptionVOS(optionMapper.findOptionByQuestionId(nextId.toString()));
                return questionVO2;

            } else if (StringUtils.isBlank(nextQuestionId)) {
//        第一次查询没有下一题问题id,返回第一题的信息
//            获取第一个问题并返回
                Question firstInfo = questionMapper.findFirstInfo(1, scaleId);
                QuestionVO2 firstQuestionVO2 = new QuestionVO2();
                firstQuestionVO2.setNextQuestionId(questionMapper.findNextId(2, scaleId));
                firstQuestionVO2.setQuestionSort(1);
                firstQuestionVO2.setQuestion(firstInfo.getQuestion());
                firstQuestionVO2.setOptionVOS(optionMapper.findOptionByQuestionId(firstInfo.getId()));
                return firstQuestionVO2;
            } else {
                ExceptionCast.cast(EvaluationCode.GET_QUESTION_ERROR);
                return null;
            }
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
            Float sum = Float.valueOf(0);
            if (StringUtils.isNotBlank(userId)) {
//            得出分数并存储选项
                for (String optionId : optionIds) {
                    Option UserOption = optionMapper.getOptionList1(optionId);
                    Float aFloat = UserOption.getScore();
                    sum += aFloat;
//                拼接字符串
                    questionAndOption.append(UserOption.getQuestionId() + "|" + UserOption.getAnswer() + ",");
                }
//            存入到选项表
                insertUserOption(userId, scaleId, questionAndOption.toString());
            } else {
//            如果没有登录就不记录，直接返回分数
                for (String optionId : optionIds) {
                    Float integer = optionMapper.getScore(optionId);
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
     * 根据量表名和用户id查询用户选项
     *
     * @param scaleId
     * @param userId
     * @return
     */
    public UserOptionVO findUserOption(String scaleId, String userId) {
        if (StringUtils.isEmpty(scaleId) || StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        try {
//        查询用户的选项
            UserOption userOption = new UserOption();
            userOption.setScaleId(scaleId);
            userOption.setUserId(userId);
            UserOption selectOne = userOptionMapper.selectOne(userOption);
            Scale scale = scaleMapper.selectByPrimaryKey(scaleId);
            String questionAndOption = selectOne.getQuestionAndOption();
//        准备问题和选项map
            List<Map> result2 = new ArrayList<>();
//        解析字符串
            String[] splits = StringUtils.split(questionAndOption, ",");
//        这样把每个选项分出来，但是问题id和答案还在一起
            for (String split : splits) {
                String question = StringUtils.substringBefore(split, "|");
                String option = StringUtils.substringAfter(split, "|");
                String questionName = questionMapper.findQuestionName(question);
                Map result = new HashMap<>();
                result.put(questionName, option);
                result2.add(result);
            }
//            因为有的是多选，所以需要合并value到一个key中
            Map combine = MyMapUtils.mapValueCombine(result2);
//        准备返回数据
            UserOptionVO userOptionVO = new UserOptionVO();
            userOptionVO.setId(selectOne.getId());
            userOptionVO.setScaleName(scale.getScaleName());
            userOptionVO.setUserName("暂时没有"); //TODO 获取用户名称
            userOptionVO.setResult(combine);
            userOptionVO.setTime(MyDateUtils.dateToString1(selectOne.getTime()));
//        返回
            return userOptionVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取匹配的量表描述
     */
    private Description getDescription(String scaleId, Float sum) {
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
    private void insertResult(String userId, String scaleId, String descriptionId, Float score, String warningId) {
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

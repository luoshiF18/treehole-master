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
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private WarningFindInfo warningFindInfo;
    @Autowired
    private ScaleTypeMapper scaleTypeMapper;

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
    public QueryResult findScale(Integer page, Integer size, String sortBy, Boolean desc, String key, String typeId) {
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
        if (StringUtils.isNotBlank(typeId)) {
            example.createCriteria().orEqualTo("typeId", typeId);
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
        for (Scale scale : scales) {
//            获取分类名称
            String typeId2 = scale.getTypeId();
            ScaleType scaleType = scaleTypeMapper.selectByPrimaryKey(typeId2);
            scale.setTypeName(scaleType.getScaleType());
            scale.setLetter(StringUtils.substring(scale.getLetter(), 0, 1));
            scale.setScaleTypeName(scale.getScaleType() == 0 ? "普通" : "跳题");
//            获取状态并判断
            scale.setStatusName(scale.getStatus() == 0 ? "未启用" : "已启用");
        }
//        解析分页结果
        PageInfo<Scale> pageInfo = new PageInfo<>(scales);

        return new QueryResult(scales, pageInfo.getTotal());
    }

    /**
     * 得到量表详细信息
     *
     * @param scaleName
     * @return
     */
    public ScaleDetailVO2 findScaleDetail(String scaleId, String scaleName) {
        try {
//            查询量表
            Scale scale2 = new Scale();
            if (StringUtils.isNotBlank(scaleId)) {
                scale2.setId(scaleId);
            }
            if (StringUtils.isNotBlank(scaleName)) {
                scale2.setScaleName(scaleName);
            }
            Scale scale = scaleMapper.selectOne(scale2);
//            填入展示类
            ScaleDetailVO2 scaleDetailVO = new ScaleDetailVO2();
//            拷贝
            BeanUtils.copyProperties(scale, scaleDetailVO);
            scaleDetailVO.setCreateTime(MyDateUtils.dateToString1(scale.getCreateTime()));
            if (scale.getUpdateTime() != null && scale.getUpdateUserId() != null) {
                scaleDetailVO.setUpdateTime(MyDateUtils.dateToString1(scale.getUpdateTime()));
            }
//            获取分类
            scaleDetailVO.setType(scaleTypeMapper.selectByPrimaryKey(scale.getTypeId()).getScaleType());
//            设置类型
            scaleDetailVO.setScaleTypeName(scale.getScaleType() == 0 ? "普通" : "跳题");
            scaleDetailVO.setStatusName(scale.getStatus() == 0 ? "未启用" : "已启用");
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
//                初始化下一个问题id和当前的问题
                StringBuilder nextId = new StringBuilder();
                StringBuilder question = new StringBuilder();
//                初始化返回问题对象
                QuestionVO2 questionVO2 = new QuestionVO2();
//                初始化序号
                Integer sort = 0;
//            如果有跳题处理
                Option option = optionMapper.getOptionList1(optionId);
                String skipId = option.getSkipId();
                Integer skip = option.getSkip();
//                如果有跳转id，并且跳转状态为开启就跳转
                if (skip == 1 && skipId != null) {
                    Question select = questionMapper.selectByPrimaryKey(skipId);
                    sort = select.getSort();
//                    设置跳题的下一题id
                    nextId.append(questionMapper.findNextId(sort + 1, scaleId));
//                    设置跳到的那道题的问题
                    question.append(select.getQuestion());
//                    获取返回问题的选项
                    List<OptionVO> option1 = optionMapper.findOptionByQuestionId(select.getId());
                    questionVO2.setOptionVOS(option1);

                } else {
//            如果没有跳题处理
                    Question questionInfo = questionMapper.selectByPrimaryKey(nextQuestionId);
                    sort = questionSort + 1;
                    nextId.append(questionMapper.findNextId(sort + 1, scaleId));
                    question.append(questionInfo.getQuestion());
//                    获取返回问题的选项
                    List<OptionVO> option1 = optionMapper.findOptionByQuestionId(questionInfo.getId());
                    questionVO2.setOptionVOS(option1);
                }
//            准备返回数据
                questionVO2.setNextQuestionId(nextId.toString());
                questionVO2.setQuestionSort(sort);
                questionVO2.setQuestion(question.toString());
//                questionVO2.setOptionVOS(optionVOS);
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
                    Question questionName = questionMapper.findQuestionName(UserOption.getQuestionId());
                    questionAndOption.append(
                            questionName.getSort() + "：" + questionName.getQuestion() + "|" + UserOption.getAnswer() + ","
                    );
                }
/*//            存入到选项表
                insertUserOption(userId, scale.getScaleName(), questionAndOption.toString());*/
            } else {
//            如果没有登录就不记录，直接返回分数
                for (String optionId : optionIds) {
                    Float integer = optionMapper.getScore(optionId);
                    sum += integer;
                }
            }
//        获取量表描述
            Description description = getDescription(scaleId, sum);
//        准备展示数据
            ResultVO resultVO = new ResultVO();
            resultVO.setWarningInfo(description.getWarningMessage());
            resultVO.setScaleName(scale.getScaleName());
            resultVO.setDescriptionInfo(description.getDescription());
            resultVO.setScore(sum);
//            如果用户不为空
            if (StringUtils.isNotBlank(userId)) {
//            预警等级
                Integer warningLevel = description.getWarningLevel();
//            存入用户名称
                resultVO.setUserName(userId);
//            存入用户结果表
                insertResult(userId, scale.getScaleName(), description.getDescription(), sum, warningLevel, description.getWarningMessage());
//            存入到选项表
                insertUserOption(userId, scale.getScaleName(), questionAndOption.toString());
//            如果预警等级超过2级存入预警信息表
                if (warningLevel >= 2) {
//                存入预警表
                    insertWarningInfo(scaleId, userId, warningLevel, description.getWarningMessage());
                }
            }
            resultVO.setResultTime(MyDateUtils.dateToString1(new Date()));
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
     * @param scaleName
     * @param userId
     * @return
     */
    public UserOptionVO findUserOption(String scaleName, String userId) {
        if (StringUtils.isEmpty(scaleName) || StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        try {
//        查询用户的选项
            UserOption userOption = new UserOption();
            userOption.setScaleName(scaleName);
            userOption.setUserId(userId);
            UserOption selectOne = userOptionMapper.selectOne(userOption);
//            Scale scale = scaleMapper.selectByPrimaryKey(scaleId);
            String questionAndOption = selectOne.getQuestionAndOption();
//        准备问题和选项map
            List<Map> result2 = new ArrayList<>();
//        解析字符串
            String[] splits = StringUtils.split(questionAndOption, ",");
//        这样把每个选项分出来，但是问题和答案还在一起
            for (String split : splits) {
                String question = StringUtils.substringBefore(split, "|");
                String option = StringUtils.substringAfter(split, "|");
                Map result = new HashMap<>();
                result.put(question, option);
                result2.add(result);
            }
//            因为有的是多选，所以需要合并value到一个key中
            Map combine = MyMapUtils.mapValueCombine(result2);
//        准备返回数据
            UserOptionVO userOptionVO = new UserOptionVO();
            userOptionVO.setId(selectOne.getId());
            userOptionVO.setScaleName(selectOne.getScaleName());
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
     * 根据量表名或用户id查询用户结果
     *
     * @param scaleName
     * @param userId
     * @return
     */
    public QueryResult findResult(Integer page, Integer size, String scaleName, String userId) {
        try {
//        分页
            PageHelper.startPage(page, size);
            Result result = new Result();
//        过滤
            if (StringUtils.isNotBlank(scaleName)) {
                result.setScaleName(scaleName);
            }
//        如果userId不为空就添加条件
            if (StringUtils.isNotBlank(userId)) {
                result.setUserId(userId);
            }
//        开始查询
            List<Result> results = resultMapper.select(result);
//        准备返回数据集合
            List<ResultVO> resultVOS = new ArrayList<>();
            for (Result result1 : results) {
//            准备返回对象
                ResultVO resultVO = new ResultVO();
//                拷贝
                BeanUtils.copyProperties(result1, resultVO);
                resultVO.setUserName("暂时没有"); //TODO 获取用户名字
//                设置描述信息
                resultVO.setDescriptionInfo(result1.getDescription());
                resultVO.setResultTime(MyDateUtils.dateToString1(result1.getCreateTime()));
                if (result1.getUpdateTime() != null && result1.getUpdateUserId() != null) {
                    String updateUserId = result1.getUpdateUserId();
                    resultVO.setUpdateUserName(updateUserId); //TODO 暂时用更改人id代替
                    resultVO.setUpdateTime(MyDateUtils.dateToString1(result1.getUpdateTime()));
                }
                resultVOS.add(resultVO);
            }
            //        解析分页
            PageInfo<ResultVO> pageInfo = new PageInfo<>(resultVOS);

            return new QueryResult(resultVOS, pageInfo.getTotal());
        } catch (Exception e) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
            return null;
        }
    }

    /**
     * 获取要更改的问题和选项
     *
     * @param scaleId
     * @return
     */
    public List<QuestionVO3> findUpdateQuestion(String scaleId) {
        try {

            if (StringUtils.isEmpty(scaleId)) {
                ExceptionCast.cast(EvaluationCode.SCALE_FIND_ERROR);
            }
//        获取该量表的所有问题
            Question question = new Question();
            question.setScaleId(scaleId);
            List<Question> questions = questionMapper.select(question);
//        问题集合对象
            List<QuestionVO3> questionVO3s = new ArrayList<>();
//        准备选项集合
            for (Question question1 : questions) {
                //        准备展示对象
                QuestionVO3 questionVO3 = new QuestionVO3();
//            获取问题
                questionVO3.setId(question1.getId());
                questionVO3.setScaleId(question1.getScaleId());
                questionVO3.setSort(question1.getSort());
                questionVO3.setQuestion(question1.getQuestion());
/*                questionVO3.setCreateTime(MyDateUtils.dateToString1(question1.getCreateTime()));
                questionVO3.setCreateUserId(question1.getCreateUserId());
//                如果有更新时间就加上更新时间
                if (question1.getUpdateTime() != null && question1.getUpdateUserId() != null) {
                    questionVO3.setUpdateTime(MyDateUtils.dateToString1(question1.getUpdateTime()));
                    questionVO3.setUpdateUserId(question1.getUpdateUserId());
                }
                questionVO3.setRemark(question1.getRemark());*/
//            获取选项
                Option option = new Option();
                option.setQuestionId(question1.getId());
                List<Option> options = optionMapper.select(option);
//                如果有跳题，弄出来题目序号
                for (Option option1 : options) {
                    option1.setSkipName("否");
                    if (option1.getSkip() == 1 && StringUtils.isNotBlank(option1.getSkipId())) {
                        option1.setSkipName("是");
//                        序号，因为添加时没有转换成问题id而是问题序号，所以判断情况
                        if (option1.getSkipId().length() < 4) {
                            option1.setSkipQuestionSort(Integer.valueOf(option1.getSkipId()));
                        } else {
                            option1.setSkipQuestionSort(questionMapper.selectByPrimaryKey(option1.getSkipId()).getSort());
                        }
                    }
                }
                questionVO3.setOptions(options);
                questionVO3s.add(questionVO3);
            }
            return questionVO3s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 搜索所有量表描述
     *
     * @param scaleId
     * @return
     */
    public QueryResult findScaleDesc(String scaleId) {
        if (StringUtils.isNotBlank(scaleId)) {
//            找到所有该量表的描述
            Description description = new Description();
            description.setScaleId(scaleId);
            List<Description> descriptions = descriptionMapper.select(description);
//            处理时间
/*            for (Description description1 : descriptions) {
                description1.setCrTime(MyDateUtils.dateToString2(description1.getCreateTime(), "yyyy-MM-dd"));
                description1.setCreateUserName(description1.getCreateUserId()); // 用id代替
//               如果有更新信息
                if (description1.getUpdateTime() != null && description1.getUpdateUserId() != null) {
                    description1.setUpTime(MyDateUtils.dateToString2(description1.getUpdateTime(), "yyyy-MM-dd"));
                    description1.setUpdateUserName(description1.getUpdateUserId()); //用id代替
                }
            }*/
//            获取总条数
            Long size = Long.valueOf(descriptions.size());
            return new QueryResult(descriptions, size);
        } else {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
            return null;
        }

    }

    /**
     * 根据分类id查询量表
     *
     * @param page
     * @param size
     * @param scaleTypeId
     * @return
     */
    public QueryResult findScaleByType(Integer page, Integer size, String scaleTypeId, Boolean isFree) {
//        分页
        PageHelper.startPage(page, size);
//        查询
        Scale scale = new Scale();
        scale.setTypeId(scaleTypeId);
        ScaleType scaleType = scaleTypeMapper.selectByPrimaryKey(scaleTypeId);
//        获取该分类不免费的量表
        if (!isFree) {
            List<Scale> notFree = scaleMapper.findScaleByNotFree(scaleTypeId);
//            设置分类名称
            for (Scale scale1 : notFree) {
                scale1.setTypeName(scaleType.getScaleType());
            }
            PageInfo<Scale> pageInfo = new PageInfo<>(notFree);
            return new QueryResult(notFree, pageInfo.getTotal());
        } else {
//        获取该分类免费的量表
            List<Scale> scales = scaleMapper.select(scale);
//            设置分类名称
            for (Scale scale1 : scales) {
                scale1.setTypeName(scaleType.getScaleType());
            }
            PageInfo<Scale> pageInfo = new PageInfo<>(scales);
            return new QueryResult(scales, pageInfo.getTotal());
        }
    }


    /**
     * 根据量表id查询下一个要添加的问题id
     *
     * @param scaleId
     * @return
     */
    public Integer findNextQuestionSort(String scaleId) {
        if (StringUtils.isEmpty(scaleId)) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        Question question = new Question();
        question.setScaleId(scaleId);
        List<Question> questions = questionMapper.select(question);
        Integer next = 0;
//        赋值给next
        for (Question question1 : questions) {
            next = next < question1.getSort() ? question1.getSort() : next;
        }
        return next + 1;
    }

    /**
     * 获取所有分类
     *
     * @return
     */
    public QueryResult findScaleType() {
        List<ScaleType> scaleTypes = scaleTypeMapper.selectAll();
        if (CollectionUtils.isEmpty(scaleTypes)) {
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }

        return new QueryResult(scaleTypes, scaleTypes.size());
    }


    /**
     * 获取一个问题的信息
     *
     * @param questionId
     * @return
     */
    public QuestionVO3 findOneQuestion(String questionId) {
        if (StringUtils.isEmpty(questionId)) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
//        搜索一个问题的信息
        Question question = questionMapper.selectByPrimaryKey(questionId);
        QuestionVO3 questionVO3 = new QuestionVO3();
//        拷贝
        BeanUtils.copyProperties(question, questionVO3);
//        获取选项
        Option option = new Option();
        option.setQuestionId(questionId);
        List<Option> options = optionMapper.select(option);
//        处理序号
        for (Option option1 : options) {
            if (option1.getSkip() == 1 && StringUtils.isNotBlank(option1.getSkipId())) {
                Question question1 = questionMapper.selectByPrimaryKey(option1.getSkipId());
//                用于展示
                option1.setSkipId(String.valueOf(question1.getSort()));
            }
        }
        questionVO3.setOptions(options);
//        返回
        return questionVO3;

    }

    /**
     * 获取一个得分描述的信息
     *
     * @param descId
     * @return
     */
    public Description findOneDescription(String descId) {
        if (StringUtils.isEmpty(descId)) {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        return descriptionMapper.selectByPrimaryKey(descId);
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
    private void insertUserOption(String userId, String scaleName, String questionAndOption) {
//        准备数据
        UserOption userOption = new UserOption();
        userOption.setId(MyNumberUtils.getUUID());
        userOption.setUserId(userId);
        userOption.setScaleName(scaleName);
        userOption.setQuestionAndOption(questionAndOption);
        userOption.setTime(new Date());
//        存入
        userOptionMapper.insert(userOption);
    }

    /**
     * 存入到结果表中
     */
    private void insertResult(String userId, String scaleName, String description, Float score, Integer warningLevel, String warningInfo) {
//        准备数据
        Result result = new Result();
        result.setId(MyNumberUtils.getUUID());
        result.setUserId(userId);
//        result.setScaleId(scaleId);
        result.setScaleName(scaleName);
        result.setDescription(description);
        result.setScore(score);
        result.setResultType(0);
        result.setCreateTime(new Date());
        result.setWarningInfo("预警等级：" + warningLevel + "级 " + "描述为：" + warningInfo);
//         存入
        resultMapper.insert(result);
    }

    /**
     * 存入预警信息
     */
    private void insertWarningInfo(String scaleId, String userId, Integer warningLevel, String w_message) {
        Warning warning = new Warning();
        warning.setId(MyNumberUtils.getUUID());
        warning.setScaleId(scaleId);
        warning.setUserId(userId);
        warning.setStatus(0);
        warning.setWarningLevel(warningLevel);
        warning.setWMessage(w_message);
        warning.setCreateTime(new Date());
        warningFindInfo.insert(warning);
    }


}

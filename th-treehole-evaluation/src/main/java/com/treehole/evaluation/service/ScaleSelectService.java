package com.treehole.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.dao.OptionMapper;
import com.treehole.evaluation.dao.QuestionMapper;
import com.treehole.evaluation.dao.ScaleMapper;
import com.treehole.framework.domain.evaluation.Option;
import com.treehole.framework.domain.evaluation.Question;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.OptionVO;
import com.treehole.framework.domain.evaluation.vo.QuestionVO;
import com.treehole.framework.domain.evaluation.vo.TestDetailVO;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
        testDetailVO.setCreateTime(scale.getCreateTime());
        testDetailVO.setImages(scale.getImages());
        testDetailVO.setType(scale.getTypeId()); //TODO 这里需要搜索详细类型名称

//        获取量表问题
        Question question = new Question();
        question.setScaleId(scaleId);
//        问题展示集合
        List<QuestionVO> questionVOList = new ArrayList<>();
        List<Question> questions = questionMapper.select(question);
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
    }
}

package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.Question;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
public interface QuestionMapper extends Mapper<Question> {

    @Select("SELECT id FROM test_question WHERE scaleId = #{scaleId} AND sort = #{sort}")
    public String findNextId(Integer sort, String scaleId);

    @Select("SELECT id,question FROM test_question WHERE scaleId = #{scaleId} AND sort = #{sort}")
    public Question findFirstInfo(Integer sort, String scaleId);

    @Select("SELECT sort FROM test_question WHERE id = #{id}")
    public Integer findSort(String id);

    @Select("SELECT id,sort,question FROM test_question WHERE scaleId = #{scaleId} AND sort = #{sort}")
    public Question findQuestionInfo(Integer sort, String scaleId);

    @Select("SELECT sort,question FROM test_question WHERE id = #{id}")
    public Question findQuestionName(String questionId);

    @Select("SELECT id FROM test_question WHERE scaleId = #{scaleId} AND sort = #{sort}")
    public String findQuestionIdBySortAndScaleId(String scaleId, String sort);

}

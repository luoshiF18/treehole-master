package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.Option;
import com.treehole.framework.domain.evaluation.vo.OptionVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
public interface OptionMapper extends Mapper<Option> {

    @Insert("INSERT INTO test_option VALUES(#{id},#{questionId},#{sort},#{answer},#{score},#{skip},#{skipId},#{scaleId})")
    public Integer insertList(String id, String questionId, Integer sort, String answer, Float score, Integer skip, String skipId, String scaleId);

    @Select("SELECT score FROM test_option WHERE id = #{id}")
    public Float getScore(String id);

    @Select("SELECT id,questionId,score FROM test_option WHERE scaleId = #{scaleId}")
    public List<Option> selectOptionList(String scaleId);

    @Select("SELECT id,questionId,score,answer,skip,skipId FROM test_option WHERE id = #{id}")
    public Option getOptionList1(String id);

    @Select("SELECT id,sort,answer FROM test_option WHERE questionId = #{questionId}")
    public List<OptionVO> findOptionByQuestionId(String questionId);

    @Select("SELECT id FROM test_option WHERE skipId = #{skipId}")
    public List<Option> findOptionBySkipId(String skipId);

 /*   @Select("SELECT id,score FROM test_option WHERE id = #{id}")
    public Option getOptionList2(String id);*/


}

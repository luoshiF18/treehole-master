package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.Option;
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
    public Integer insertList(String id, String questionId, Integer sort, String answer, Integer score, Integer skip, Integer skipId, String scaleId);

    @Select("SELECT score FROM test_option WHERE id = #{id}")
    public Integer getScore(String id);

    @Select("SELECT id,questionId,score FROM test_option WHERE scaleId = #{scaleId}")
    public List<Option> selectOptionList(String scaleId);

    @Select("SELECT id,questionId,score,answer FROM test_option WHERE id = #{id}")
    public Option getOptionList1(String id);

 /*   @Select("SELECT id,score FROM test_option WHERE id = #{id}")
    public Option getOptionList2(String id);*/


}

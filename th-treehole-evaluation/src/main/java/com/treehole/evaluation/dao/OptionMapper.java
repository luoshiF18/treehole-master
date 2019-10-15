package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.Option;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
public interface OptionMapper extends Mapper<Option> {

    @Insert("INSERT INTO test_option VALUES(#{id},#{questionId},#{sort},#{answer},#{score},#{skip},#{skipId})")
    public Integer insertList(String id, String questionId, Integer sort, String answer, Integer score, Integer skip, Integer skipId);
}

package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.Scale;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
public interface ScaleMapper extends Mapper<Scale> {

    @Select("SELECT * FROM test_scale WHERE typeId =#{scaleTypeId} AND price > 0")
    public List<Scale> findScaleByNotFree(String scaleTypeId);


    @Select("SELECT id FROM test_scale WHERE scaleName =#{ scaleName}")
    public String findScaleIdByName(String scaleName);

}

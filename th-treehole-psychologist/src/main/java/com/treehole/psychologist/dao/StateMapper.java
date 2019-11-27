package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.State;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/10/29 19:17
 */
public interface StateMapper extends Mapper<State> {

    //按照id自增查询所有简介信息
    @Select("select * from state order by id+0")
    List<State> getAllStates();

}

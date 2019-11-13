package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.State;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/13 15:24
 */

@Mapper
@Repository
public interface StatesMapper {

    /**
     * 按照id自增查询所有状态信息
     *
     * @return
     */
    List<State> getAll();
}

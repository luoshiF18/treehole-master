package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Detail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/16 9:30
 */
@Mapper
public interface DetailsMapper {

    /**
     * 按照id自增分页查询所有咨询师详情信息
     *
     * @return
     */
    List<Detail> getAllByPage();
}

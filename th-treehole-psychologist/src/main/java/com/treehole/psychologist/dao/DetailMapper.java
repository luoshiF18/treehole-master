package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Detail;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/16 10:12
 */
public interface DetailMapper extends Mapper<Detail> {

    //按照id自增分页查询所有咨询师详情信息
    @Select("select * from detail order by psychologist_id+0")
    List<Detail> getAllDetails();
}

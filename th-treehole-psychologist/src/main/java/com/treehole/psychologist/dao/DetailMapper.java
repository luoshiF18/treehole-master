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

    //查询所有咨询师获得的好评数
    @Select("SELECT * FROM detail")
    List<Detail> getPsychologists();

}

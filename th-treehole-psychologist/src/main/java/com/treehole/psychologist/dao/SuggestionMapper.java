package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Suggestion;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/19 14:52
 */
public interface SuggestionMapper extends Mapper<Suggestion> {

    //分页查询所有建议信息
    @Select("select * from suggestion order by suggestion_id+0")
    List<Suggestion> getAllSuggestions();
}

package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/23 9:37
 */
public interface CommentMapper extends Mapper<Comment> {

    /**
     * 查询所有评价信息
     *
     * @return
     */
    @Select("select * from comment order by comment_id+0")
    List<Comment> getAllComments();

    /**
     * 根据咨询师id分页查询该咨询师的评价信息
     *
     * @param psychologist_id 咨询师id
     * @return
     */
    @Select("select *  from comment where psychologist_id = #{psychologist_id} order by comment_id+0")
    List<Comment> getCommentsByPsyId(@Param("psychologist_id") String psychologist_id);

}

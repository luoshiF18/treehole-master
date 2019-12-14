package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.Comment;
import com.treehole.framework.domain.psychologist.ext.CommentExt;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/11/23 9:23
 */
@Api(value = "用户评价信息相关接口", description = "对用户评价信息进行管理")
public interface CommentControllerApi {

    @ApiOperation("分页查询所有评价信息")
    QueryResponseResult getAllCommentsByPage(Integer page, Integer size);

    @ApiOperation("根据咨询师id分页查询该咨询师的评价信息")
    QueryResponseResult getCommentsByPsyId(Integer page, Integer size, String psychologist_id);

    @ApiOperation("根据评价id查询评价信息")
    CommentExt getAllCommentById(String comment_id);

    @ApiOperation("根据评价id删除评价信息")
    ResponseResult delCommentByCommentId(String comment_id);

    @ApiOperation("更新评价信息")
    ResponseResult updateComment(Comment comment);

    @ApiOperation("添加评价信息")
    ResponseResult addComment(Comment comment);

}

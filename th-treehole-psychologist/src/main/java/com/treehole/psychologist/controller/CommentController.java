package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.CommentControllerApi;
import com.treehole.framework.domain.psychologist.Comment;
import com.treehole.framework.domain.psychologist.ext.CommentExt;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Helay
 * @date 2019/11/23 9:26
 */
@RestController
@RequestMapping("/psychologist/comment")
public class CommentController implements CommentControllerApi {

    @Autowired
    private CommentService commentService;

    /**
     * 分页查询所有评价信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    @Override
    @GetMapping("/get/list/")
    public QueryResponseResult getAllCommentsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        return this.commentService.getAllCommentsByPage(page, size);
    }

    /**
     * 根据咨询师id分页查询该咨询师的评价信息
     *
     * @param page            当前页
     * @param size            每页记录数
     * @param psychologist_id 咨询师id
     * @return
     */
    @Override
    @GetMapping("/get/psy/{psychologist_id}")
    public QueryResponseResult getCommentsByPsyId(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @PathVariable("psychologist_id") String psychologist_id
    ) {
        return this.commentService.getCommentsByPsyId(page, size, psychologist_id);
    }

    /**
     * 根据评价id查询评价信息
     *
     * @param comment_id 评价id
     * @return
     */
    @Override
    @GetMapping("/get/comment/{comment_id}")
    public CommentExt getAllCommentById(@PathVariable("comment_id") String comment_id) {
        return this.commentService.getAllCommentById(comment_id);
    }

    /**
     * 根据评价id删除评价信息
     *
     * @param comment_id 评价id
     * @return
     */
    @Override
    @DeleteMapping("/del/{comment_id}")
    public ResponseResult delCommentByCommentId(@PathVariable("comment_id") String comment_id) {
        this.commentService.delCommentByCommentId(comment_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更新评价信息
     *
     * @param comment
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateComment(@RequestBody Comment comment) {
        this.commentService.updateComment(comment);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加评价信息
     *
     * @param comment
     * @return
     */
    @Override
    @PostMapping("/add")
    public ResponseResult addComment(@RequestBody Comment comment) {
        this.commentService.addComment(comment);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}

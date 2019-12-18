package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.User;

import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.psychologist.Comment;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.ext.CommentExt;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.client.UserClient;
import com.treehole.psychologist.dao.CommentMapper;
import com.treehole.psychologist.dao.ProfileMapper;
import com.treehole.psychologist.util.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Helay
 * @date 2019/11/23 9:31
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private UserClient userClient;

    /**
     * 分页查询所有评价信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    public QueryResponseResult getAllCommentsByPage(Integer page, Integer size) {
        //设置分页参数
        PageHelper.startPage(page, size);
        //查询，获取comment集合
        List<Comment> comments = this.commentMapper.getAllComments();
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(comments)) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        //comment集合转换为commentExt集合
        List<CommentExt> commentExts = comments.stream().map((Comment comment) -> {
            CommentExt commentExt = new CommentExt();
            BeanUtils.copyProperties(comment, commentExt);// copy共同属性的值到新的对象
            //查询心理咨询师信息
            Profile profile = this.profileMapper.selectByPrimaryKey(comment.getPsychologist_id());
            if (profile == null) {
                ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
            }
            //设置咨询师姓名
            commentExt.setPsychologist_name(profile.getName());
            //查询用户信息

            UserVo user = this.userClient.getUserVoByUserId(  comment.getUser_id());

            if (user == null) {
                ExceptionCast.cast(PsychologistCode.USER_NOT_EXIST);
            }
            //设置用户昵称
            commentExt.setUser_nickname(user.getUser_nickname());
            return commentExt;
        }).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(commentExts);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据咨询师id分页查询该咨询师的评价信息
     *
     * @param page            当前页
     * @param size            每页记录数
     * @param psychologist_id 咨询师id
     * @return
     */
    public QueryResponseResult getCommentsByPsyId(Integer page, Integer size, String psychologist_id) {
        //判断参数是否为空
        if (StringUtils.isBlank(psychologist_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        //设置分页参数
        PageHelper.startPage(page, size);
        //开始查询
        List<Comment> comments = this.commentMapper.getCommentsByPsyId(psychologist_id);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(comments)) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        //将comment集合转换为commentExt集合
        List<CommentExt> commentExtList = comments.stream().map(comment -> {
            CommentExt commentExt = new CommentExt();
            //copy相同属性的值到新对象
            BeanUtils.copyProperties(comment, commentExt);
            //查询心理咨询师信息
            Profile psy = this.profileMapper.selectByPrimaryKey(comment.getPsychologist_id());
            if (psy == null) {
                ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
            }
            //设置咨询师姓名
            commentExt.setPsychologist_name(psy.getName());
            //查询用户信息

            UserVo user = this.userClient.getUserVoByUserId(  comment.getUser_id());

            if (user == null) {
                ExceptionCast.cast(PsychologistCode.USER_NOT_EXIST);
            }
            //设置用户昵称
            commentExt.setUser_nickname(user.getUser_nickname());
            return commentExt;
        }).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(commentExtList);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据评价id删除评价信息
     *
     * @param comment_id 评价id
     * @return
     */
    @Transactional
    public ResponseResult delCommentByCommentId(String comment_id) {
        Comment comment = this.commentMapper.selectByPrimaryKey(comment_id);
        if (comment == null) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        if (StringUtils.isBlank(comment_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_ERROR);
        }
        int i = this.commentMapper.deleteByPrimaryKey(comment_id);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更新评价信息
     *
     * @param comment 评价信息
     */
    @Transactional
    public ResponseResult updateComment(Comment comment) {
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        Comment one = this.commentMapper.selectByPrimaryKey(comment.getComment_id());
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        //根据id更新
        criteria.andEqualTo("comment_id", comment.getComment_id());
        one.setComment_id(one.getComment_id());
        one.setOrder_id(one.getOrder_id());
        one.setConsultation_id(one.getConsultation_id());
        one.setUser_id(one.getUser_id());
        one.setPsychologist_id(one.getPsychologist_id());
        int key = this.commentMapper.updateByExample(comment, example);
        if (key != 1) {
            ExceptionCast.cast(PsychologistCode.UPDATE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加评价信息
     *
     * @param comment 评价信息
     */
    @Transactional
    public ResponseResult addComment(Comment comment) {
        if (comment == null) {
            ExceptionCast.cast(PsychologistCode.INSERT_DATA_NULL);
        }
        String id = MyUtils.getId();
        comment.setComment_id(id);
        comment.setCreate_time(new Date());
        comment.setUpdate_time(comment.getCreate_time());
        int i = this.commentMapper.insert(comment);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据评价id查询评价信息
     *
     * @param comment_id 评价id
     * @return
     */
    public CommentExt getAllCommentById(String comment_id) {
        if (StringUtils.isBlank(comment_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        Comment comment = this.commentMapper.selectByPrimaryKey(comment_id);
        if (comment == null) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        Profile profile = this.profileMapper.selectByPrimaryKey(comment.getPsychologist_id());
        if (profile == null) {
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }

        UserVo user = this.userClient.getUserVoByUserId(  comment.getUser_id());

        if (user == null) {
            ExceptionCast.cast(PsychologistCode.USER_NOT_EXIST);
        }
        //设置数据
        CommentExt commentExt = new CommentExt();
        BeanUtils.copyProperties(comment, commentExt);
        commentExt.setUser_nickname(user.getUser_nickname());
        commentExt.setPsychologist_name(profile.getName());
        return commentExt;
    }
}

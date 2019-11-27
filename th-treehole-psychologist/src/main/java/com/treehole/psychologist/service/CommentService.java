package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.treehole.framework.domain.psychologist.Comment;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.ext.CommentExt;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.psychologist.dao.CommentMapper;
import com.treehole.psychologist.dao.ProfileMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
        //开始查询
        List<Comment> commentList = this.commentMapper.selectAll();
        //判断集合是否为空
        if (CollectionUtils.isEmpty(commentList)) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        //创建Comment扩展类的数组
        ArrayList<CommentExt> commentExts = new ArrayList<>();
        //循环遍历commentList数组，将commentList转成commentExts
        for (Comment comments : commentList) {
            CommentExt commentExt = new CommentExt();
            //获取咨询师id
            String psychologist_id = comments.getPsychologist_id();
            //根据id查询咨询师简介信息
            Profile profile = this.profileMapper.selectByPrimaryKey(psychologist_id);
            //获取咨询师姓名
            String psychologist_name = profile.getName();
            //设置数据
            commentExt.setComment_id(comments.getComment_id());
            commentExt.setUser_id(comments.getUser_id());
            commentExt.setPsychologist_id(psychologist_id);
            commentExt.setPsychologist_name(psychologist_name);
            commentExt.setOrder_id(comments.getOrder_id());
            commentExt.setConsultation_id(comments.getConsultation_id());
            commentExt.setComment_type(comments.getComment_type());
            commentExt.setComment_content(comments.getComment_content());
            commentExt.setCreate_time(comments.getCreate_time());
            commentExt.setUpdate_time(comments.getUpdate_time());
            //将commentExt对象添加到commentExts集合中
            commentExts.add(commentExt);
        }
        QueryResult queryResult = new QueryResult();
        queryResult.setList(commentExts);
        queryResult.setTotal(commentExts.size());
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
        //查询
        List<Comment> comments = this.commentMapper.getCommentsByPsyId(psychologist_id);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(comments)) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        //创建Comment扩展类的数组
        List<CommentExt> commentExts = new ArrayList<>();
        for (Comment list : comments) {
            //创建Comment扩展类的实例对象
            CommentExt commentExt = new CommentExt();
            //根据id查询咨询师简介信息
            Profile profile = this.profileMapper.selectByPrimaryKey(psychologist_id);
            //获取咨询师姓名
            String psychologist_name = profile.getName();
            //设置数据
            commentExt.setComment_id(list.getComment_id());//评价id
            commentExt.setUser_id(list.getUser_id());//用户id
            commentExt.setPsychologist_id(list.getPsychologist_id());//咨询师id
            commentExt.setPsychologist_name(psychologist_name);//咨询师姓名
            commentExt.setOrder_id(list.getOrder_id());//订单id
            commentExt.setConsultation_id(list.getConsultation_id());//咨询记录id
            commentExt.setComment_type(list.getComment_type());//评价类型
            commentExt.setComment_content(list.getComment_content());//评价内容
            commentExt.setCreate_time(list.getCreate_time());//评价信息生成时间
            commentExt.setUpdate_time(list.getUpdate_time());//评价信息更新时间
            //将commentExt对象添加到commentExts集合中
            commentExts.add(commentExt);
        }
        QueryResult queryResult = new QueryResult();
        queryResult.setList(commentExts);
        queryResult.setTotal(commentExts.size());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据用户id分页查询该咨询师的评价信息
     *
     * @param page    当前页
     * @param size    每页记录数
     * @param user_id 用户id
     * @return
     */
    public QueryResponseResult getCommentsByUserId(Integer page, Integer size, String user_id) {
        //判断参数是否为空
        if (StringUtils.isBlank(user_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        //设置分页参数
        PageHelper.startPage(page, size);
        //查询
        List<Comment> commentList = this.commentMapper.getCommentsByUserId(user_id);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(commentList)) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        //创建Comment扩展类的数组
        List<CommentExt> commentExts = new ArrayList<>();
        //循环遍历commentList，并将commentList转成commentExts
        for (Comment comment : commentList) {
            CommentExt commentExt = new CommentExt();
            Profile profile = this.profileMapper.selectByPrimaryKey(comment.getPsychologist_id());
            //设置数据
            commentExt.setComment_id(comment.getComment_id());//评价id
            commentExt.setUser_id(comment.getUser_id());//用户id
            commentExt.setPsychologist_id(comment.getPsychologist_id());//咨询师id
            commentExt.setPsychologist_name(profile.getName());//咨询师姓名
            commentExt.setOrder_id(comment.getOrder_id());//订单id
            commentExt.setConsultation_id(comment.getConsultation_id());//咨询记录id
            commentExt.setComment_type(comment.getComment_type());//评价类型
            commentExt.setComment_content(comment.getComment_content());//评价内容
            commentExt.setCreate_time(comment.getCreate_time());//评价信息生成时间
            commentExt.setUpdate_time(comment.getUpdate_time());//评价信息更新时间
            //将commentExt对象添加到commentExts集合中
            commentExts.add(commentExt);
        }
        QueryResult queryResult = new QueryResult();
        queryResult.setList(commentExts);
        queryResult.setTotal(commentExts.size());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据评价id删除评价信息
     *
     * @param comment_id 评价id
     * @return
     */
    @Transactional
    public void delCommentByCommentId(String comment_id) {
        if (StringUtils.isBlank(comment_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_ERROR);
        }
        int i = this.commentMapper.deleteByPrimaryKey(comment_id);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
    }

    /**
     * 更新评价信息
     *
     * @param comment
     */
    @Transactional
    public void updateComment(Comment comment) {
        //先查询该评价信息
        Comment one = this.commentMapper.selectByPrimaryKey(comment.getComment_id());
        //判断是否为空
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }

        int key = this.commentMapper.updateByPrimaryKey(comment);
        if (key != 1) {
            ExceptionCast.cast(PsychologistCode.UPDATE_FAIL);
        }
    }
}

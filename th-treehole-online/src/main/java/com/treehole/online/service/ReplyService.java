package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Category;
import com.treehole.framework.domain.onlinetalk.Reply;
import com.treehole.framework.domain.onlinetalk.Vo.ReplyVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.ReplyMapper;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@Service
//@Cacheable(value = "ReplyService")
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private CategoryService categoryService;


    /**
     * 查询所有快捷回复
     *
     * @param
     * @return List<User>
     */

    public QueryResult findAllReply(int page, int size , String categoryname) {
        //分页
        Page pag =PageHelper.startPage(page,size);
        List<Reply> replies = new ArrayList<>();
        if (!categoryname.equals("")&&categoryname!=null){
            Reply reply2 = new Reply();
            Category category = new Category();
            category.setCategory_name(categoryname);
            Category category1 = categoryService.findCategory(category);
            reply2.setCategory_id(category1.getCategory_id());
           replies= replyMapper.select(reply2);
        }else {
            replies = replyMapper.selectAll();
        }

        //查询
        List<ReplyVo> replyVos = new ArrayList<>();
        for (Reply reply : replies){

            ReplyVo replyVo = new ReplyVo();
            replyVo.setReply_id(reply.getReply_id());
            replyVo.setReply_title(reply.getReply_title());
            replyVo.setReply_content(reply.getReply_content());
            replyVo.setCategory( categoryService.getCategoryById(reply.getCategory_id()).getCategory_name());
            System.out.println( replyVo.getCategory());
            replyVo.setReply_creater(reply.getReply_creater());
            replyVo.setReply_createtime(reply.getReply_createtime());

            replyVos.add(replyVo);
        }

       //查询
        if (CollectionUtils.isEmpty(replyVos)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<ReplyVo> pageInfo = new PageInfo<>(replyVos);

        return new QueryResult(replyVos, pageInfo.getTotal());

    }

    /**
     * 根据reply对象查询所有reply记录
     *
     * @param
     * @return List<UserVo>
     */
    public Reply findReply(Reply reply) {
        Reply reply1 = replyMapper.selectOne(reply);
        if(reply1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return reply1;
    }





    /**
     * 通过id查询快捷回复信息
     * @return List<User>
     */
    public ReplyVo getReplyById(String reply_id){
        Reply reply1 = new Reply();
       //
        reply1.setReply_id(reply_id);
        Reply reply = replyMapper.selectOne(reply1);
        if(reply1 == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        ReplyVo replyVo = new ReplyVo();
        replyVo.setReply_id(reply.getReply_id());
        replyVo.setReply_title(reply.getReply_title());
        replyVo.setReply_content(reply.getReply_content());
        replyVo.setCategory( categoryService.getCategoryById(reply.getCategory_id()).getCategory_name());
        System.out.println( replyVo.getCategory());
        replyVo.setReply_creater(reply.getReply_creater());
        replyVo.setReply_createtime(reply.getReply_createtime());
        return replyVo;
    }

    /**
     * 通过id删除客服
     * @param reply_id
     * @return
     */
    //@CacheEvict(value="ReplyService",allEntries=true)
    public void deleteReplyById(String reply_id) {

        if(StringUtils.isBlank(reply_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        if(this.getReplyById(reply_id) == null){
            ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
        }
        Reply reply = new Reply();
        reply.setReply_id(reply_id);
        int del = replyMapper.delete(reply);

        if( del != 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }

    }

    /**
     * 创建一条快捷回复
     *
     * @param reply
     * @return int
     */
    //@CacheEvict(value="ReplyService",allEntries=true)
    public void insertReply(Reply reply)  {
        reply.setReply_id(MyNumberUtils.getUUID());

        reply.setReply_createtime(new Date());
        //
        int ins = replyMapper.insert(reply);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

    }

    /**
     * 更新快捷回复信息
     *
     *
     * @param replyVo
     * @return int
     */
    //@CacheEvict(value="ReplyService",allEntries=true)
    public void updateReply(ReplyVo replyVo){

        Reply reply = new Reply();
        reply.setReply_id(replyVo.getReply_id());
        reply.setReply_title(replyVo.getReply_title());
        reply.setReply_createtime(replyVo.getReply_createtime());
        reply.setReply_content(replyVo.getReply_content());
        reply.setReply_creater(replyVo.getReply_creater());
        Category category = new Category();
        category.setCategory_name(replyVo.getCategory());
        Category category1 = categoryService.findCategory(category);
        reply.setCategory_id(category1.getCategory_id());

        Example example =new Example(Reply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("reply_id",reply.getReply_id());

        int upd= replyMapper.updateByExampleSelective(reply,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }


}

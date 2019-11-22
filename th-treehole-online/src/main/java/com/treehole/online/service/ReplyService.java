package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Category;
import com.treehole.framework.domain.onlinetalk.Reply;
import com.treehole.framework.domain.onlinetalk.Vo.AgentVo;
import com.treehole.framework.domain.onlinetalk.Vo.ReplyVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.AgentMapper;
import com.treehole.online.mapper.ReplyMapper;
import com.treehole.online.myUtil.MyMd5Utils;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private CategoryService categoryService;


    /**
     * 查询所有用户
     *
     * @param
     * @return List<User>
     */

    public QueryResult findAllReply(int page, int size , String categoryname) {

        Page pag =PageHelper.startPage(page,size);
        List<Reply> replies = new ArrayList<>();
        if (!categoryname.equals("")){
            Reply reply2 = new Reply();
            Category category = new Category();
            category.setCategory_name(categoryname);
            Category category1 = categoryService.findCategory(category);
            reply2.setCategory_id(category1.getCategory_id());
           replies= replyMapper.select(reply2);
            System.out.println("11111");
           // replies = replyMapper.selectAll();
        }else {
            replies = replyMapper.selectAll();
        }
        System.out.println("数量1为"+replies.size());
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
        System.out.println("数量2为"+replyVos.size());

       //查询
        if (CollectionUtils.isEmpty(replyVos)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<ReplyVo> pageInfo = new PageInfo<>(replyVos);

        return new QueryResult(replyVos, pageInfo.getTotal());

    }

    /**
     * 根据user对象查询所有user记录
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
     * 根据user_nickname查询所有user记录
     *
     * @param
     * @return User
     */
   /* public User findUserByNickname(String nickname){
        if(StringUtils.isBlank(nickname)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        User user = new User();
        user.setUser_nickname(nickname);
        User use = userMapper.selectOne(user);

        return use;
    }*/

/*public UserExt getUserExt(String userNickName){
        if (StringUtils.isBlank(userNickName)){
            ExceptionCast.cast( MemberCode.DATA_ERROR);
        }
     User user = new User();
     user.setUser_nickname(userNickName);
    User use = userMapper.selectOne(user);
    UserExt userExt = new UserExt();
    BeanUtils.copyProperties(use,userExt);
    return userExt;
}*/


   /* *//**
     * 根据user_iphone查询所有user记录
     *
     * @param categoryId
     * @return Reply
     */
    public Reply findUserByCategoryId(String categoryId)  {
        Reply reply = new Reply();
        reply.setCategory_id(categoryId);
        return  replyMapper.selectOne(reply);
    }


    /**
     * 通过id查询用户
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
     * 创建一条用户信息
     *
     * @param reply
     * @return int
     */
    public void insertReply(Reply reply)  {
        reply.setReply_id(MyNumberUtils.getUUID());

        reply.setReply_createtime(new Date());
        //
        int ins = replyMapper.insert(reply);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        //往cards表中插入数据
       //cardsService.insertCard(user);
    }

    /**
     * 更新客服基本信息
     *
     *
     * @param replyVo
     * @return int
     */
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

    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}

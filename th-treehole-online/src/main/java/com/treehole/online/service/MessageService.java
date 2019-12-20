package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Message;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.MessageMapper;
import com.treehole.online.myUtil.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
@Cacheable(value = "MessageService")
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 根据userId查询聊天记录
     * @param user_id
     * @return
     */
    public List<Message> getMessageByUserId(String user_id) {
        Message message = new Message();
        message.setTouser_id(user_id);
        List<Message> messageList = messageMapper.select(message);
        if (messageList == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return messageList;
    }

    /**
     * 根据会话id查询聊天记录
     * @param convers_id
     * @return
     */

    public List<Message> getMessageByConversId(String convers_id) {
        Message message = new Message();
        message.setConvers_id(convers_id);
        List<Message> messageList = messageMapper.select(message);
        //使用排序方法进行排序
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message u1, Message u2) {
                return u1.getMessage_createtime().compareTo(u2.getMessage_createtime());  //大于返回1；小于返回-1；等于返回0
            }
        });
        if (messageList == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return messageList;
    }
    /**
     * 根据会话id查询聊天记录
     * @param convers_id
     * @return
     */
    public QueryResult getMessageByConversId(int page, int size ,String convers_id) {
        if (convers_id ==""){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //        分页
        Page pag =PageHelper.startPage(page,size);
        Message message = new Message();
        message.setConvers_id(convers_id);
        List<Message> messageList = messageMapper.select(message);
        //根据时间排序
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message u1, Message u2) {
                return u1.getMessage_createtime().compareTo(u2.getMessage_createtime());  //大于返回1；小于返回-1；等于返回0
            }
        });

        if (messageList == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        //        解析分页结果
        PageInfo<Message> pageInfo = new PageInfo<>(pag.getResult());

        return new QueryResult(messageList, pageInfo.getTotal());

    }

    /**
     * 新增聊天记录
     * @param message
     */
    @CacheEvict(value="MessageService",allEntries=true)
    public void insertMessage(Message message) {
        message.setMessage_createtime(new Date());
        System.out.println("9999999999999999"+message.getMessage_createtime());
        System.out.println(message);
        if (message == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        message.setMessage_id(MyNumberUtils.getUUID());
        int i = messageMapper.insert(message);

        if (i!=1){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }

    }

    /**
     * 根据会话id删除聊天记录
     * @param convers_id
     */
    @CacheEvict(value="MessageService",allEntries=true)
    public void deleteMessageByConversId(String convers_id) {
        List<Message> messages = this.getMessageByConversId(convers_id);
        int sum = messages.size();
        int i =0;
        for (Message message : messages){
            messageMapper.delete(message);
            i++;
        }
        if (i != sum){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }

    }


}

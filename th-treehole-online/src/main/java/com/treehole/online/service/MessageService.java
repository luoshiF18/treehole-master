package com.treehole.online.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Message;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.mapper.AgentMapper;
import com.treehole.online.mapper.MessageMapper;
import com.treehole.online.myUtil.MyMd5Utils;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;



    public List<Message> getMessageByUserId(String user_id) {
        Message message = new Message();
        message.setTouser_id(user_id);
        List<Message> messageList = messageMapper.select(message);
        if (messageList == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        return messageList;
    }

    public List<Message> getMessageByConversId(String convers_id) {
        Message message = new Message();
        message.setConvers_id(convers_id);
        List<Message> messageList = messageMapper.select(message);
        if (messageList == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        return messageList;
    }

    public void insertMessage(Message message) {
        if (message == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        int i = messageMapper.insert(message);
        if (i<0 || i==0){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }

    }

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

    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}

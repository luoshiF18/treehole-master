package com.treehole.online.controller;

import com.treehole.api.onlinetalk.AgentControllerApi;
import com.treehole.api.onlinetalk.MessageControllerApi;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Message;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.AgentService;
import com.treehole.online.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("message")
public class MessageController implements MessageControllerApi {
    @Autowired
    private MessageService messageService;





    @Override
    @GetMapping("/find/user_id/{user_id}")
    public List<Message> getMessageByUserId(String user_id) {

        return messageService.getMessageByUserId(user_id);
    }

    @Override
    @GetMapping("/find/convers_id/{convers_id}")
    public List<Message> getMessageByConversId(String convers_id) {
        return messageService.getMessageByConversId(convers_id);
    }

    @Override
    @PostMapping ("/insert")
    public ResponseResult insertMessage(@Valid Message message) {

        messageService.insertMessage(message);

        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @DeleteMapping(value ="/delete/convers_id/{convers_id}")
    public ResponseResult deleteMessageByConversId(String convers_id) {
        messageService.deleteMessageByConversId(convers_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }


}

package com.treehole.online.controller;

import com.treehole.api.onlinetalk.MessageControllerApi;
import com.treehole.framework.domain.onlinetalk.Message;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hwz
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("online/message")
public class MessageController implements MessageControllerApi {
    @Autowired
    private MessageService messageService;


    /**
     * 根据userid查找聊天记录
     * @param user_id
     * @return
     */
    @Override
    @GetMapping("/find/user_id/{user_id}")
    public List<Message> getMessageByUserId(String user_id) {

        return messageService.getMessageByUserId(user_id);
    }

    /**
     * 根据会话id查找聊天记录
     * @param page
     * @param size
     * @param convers_id
     * @return
     */
    @Override
    @GetMapping("/find/convers_id")
    public QueryResponseResult getMessageByConversId(
                                                        @RequestParam(value = "page" ,defaultValue = "1") int page,
                                                        @RequestParam(value = "size" ,defaultValue = "5") int size,
                                                        @RequestParam(value = "convers_id" ,defaultValue = "") String convers_id) {

        QueryResult queryResult = messageService.getMessageByConversId(page,size,convers_id);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }

    /**
     * 新增聊天记录
     * @param message
     * @return
     */
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertMessage(@RequestBody @Valid Message message) {

        messageService.insertMessage(message);

        return new ResponseResult(CommonCode.SUCCESS);

    }

    /**
     * 根据会话id删除聊天记录
     * @param convers_id
     * @return
     */
    @Override
    @DeleteMapping(value ="/delete/convers_id/{convers_id}")
    public ResponseResult deleteMessageByConversId(String convers_id) {
        messageService.deleteMessageByConversId(convers_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }


}

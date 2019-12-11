package com.treehole.api.onlinetalk;

import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Message;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 何文泽
 * @Description:
 * @Date
 */
@Api(value = "聊天记录信息管理", description = "对聊点记录信息进行增删改查")
public interface MessageControllerApi {

    @ApiOperation("通过用户id查询聊天记录")
    public List<Message>  getMessageByUserId(@PathVariable("user_id") String user_id)  ;
    @ApiOperation("通过会话id查询聊天记录")
    public List<Message> getMessageByConversId(@PathVariable("convers_id") String convers_id)  ;

    @ApiOperation("创建一条聊天记录信息")
    public ResponseResult insertMessage(@RequestBody @Valid Message message);

    @ApiOperation("通过会话id删除聊天记录")
    public ResponseResult deleteMessageByConversId(@PathVariable("convers_id") String convers_id) ;





}

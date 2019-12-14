package com.treehole.api.onlinetalk;

import com.treehole.framework.domain.onlinetalk.Convers;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author 何文泽
 * @Description:
 * @Date
 */
@Api(value = "会话记录信息管理", description = "对会话记录信息进行增删改查")
public interface ConversControllerApi {
    @ApiOperation("查询所有会话信息")
    public QueryResponseResult getAllConvers(int page, int size, String name);

    @ApiOperation("通过会话id查询会话信息")
    public Convers getConversById(@PathVariable("convers_id") String convers_id)  ;

    @ApiOperation("创建一条聊天记录信息")
    public ResponseResult insertConvers(@RequestBody @Valid Convers convers);

    @ApiOperation("通过会话id删除聊天记录")
    public ResponseResult deleteConversById(@PathVariable("convers_id") String convers_id) ;





}

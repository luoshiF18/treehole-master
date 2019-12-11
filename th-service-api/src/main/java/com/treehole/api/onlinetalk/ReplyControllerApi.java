package com.treehole.api.onlinetalk;

import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Reply;
import com.treehole.framework.domain.onlinetalk.Vo.ReplyVo;
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
@Api(value = "回复信息管理", description = "对回复信息进行增删改查")
public interface ReplyControllerApi {
    @ApiOperation("查询所有回复信息")
    public QueryResponseResult getAllReply(int page, int size,String category);

    @ApiOperation("通过id查询回复信息")
    public ReplyVo getReplyById(@PathVariable("id") String id)  ;

    @ApiOperation("创建一条回复信息")
    public ResponseResult insertReply(@RequestBody @Valid Reply reply);

    @ApiOperation("通过id删除回复信息")
    public ResponseResult deleteReplyById(@PathVariable("reply_id") String reply_id) ;

    @ApiOperation("更新回复基本信息")
    public ResponseResult updateReply(@RequestBody @Valid ReplyVo replyVo);

    /*@ApiOperation("更新用户手机号")
    public ResponseResult updateUserPhone(@RequestBody @Valid User user);

    @ApiOperation("根据客服对象查询用户信息")
    public User getUser(@RequestBody @Valid User user);
    @ApiOperation( "根据客服昵称得到用户扩展对象" )
    public UserExt getUserExt(@RequestParam("userNickName") String userNickName);*/

}

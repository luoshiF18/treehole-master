package com.treehole.api.member;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shanhuijie
 * @Description:
 * @Date 2019.10.21 19:47
 */
@Api(value = "用户扩展信息管理", description = "对用户扩展信息进行查询")
public interface UserVoControllerApi {
    @ApiOperation("根据传入的user对象，查询所有Vo用户信息")
    public QueryResponseResult findAllUserVo( Integer page, Integer size, User user);

    @ApiOperation("查询所有Vo用户信息")
    public QueryResponseResult getAllUserVo(Integer page, Integer size);

    /*@ApiOperation("根据用户uniq_ID查询用户Vo信息")
    public UserVo getUserVoByUniqId(@PathVariable("uniq_id") String uniq_id);
    */
    @ApiOperation("根据用户user_ID查询用户Vo信息")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id);

    @ApiOperation("根据用户user_phone查询用户Vo信息")
    public UserVo getUserVoByUserPhone(@PathVariable("user_phone") String user_phone);

    @ApiOperation("根据用户user_nickname查询用户Vo信息")
    public UserVo getUserVoByNickname(@RequestParam("nickname") String nickname);
}

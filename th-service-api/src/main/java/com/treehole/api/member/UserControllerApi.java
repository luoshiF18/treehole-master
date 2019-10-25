package com.treehole.api.member;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Api(value = "用户信息管理", description = "对用户信息进行增删改查")
public interface UserControllerApi {
    @ApiOperation("查询所有用户")
    public Result findAllUser() throws Exception;
    @ApiOperation("查询所有Vo用户信息")
    public Result findAllUserVo() throws Exception;
    @ApiOperation("通过id查询用户")
    public Result getUserById(@PathVariable String id) throws Exception;

    @ApiOperation("创建一条用户信息")
    public Result insertUser(@RequestBody @Valid User user) throws Exception;

    @ApiOperation("通过id删除用户")
    public Result deleteUserById(@PathVariable("id")String user_id) throws Exception;

    @ApiOperation("更新用户基本信息")
    public Result update(@RequestBody @Valid User user) throws Exception;

    @ApiOperation("更新用户手机号")
    public Result updateUserPhone(@RequestBody @Valid User user) throws Exception;



}

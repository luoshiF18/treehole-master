package com.treehole.api.member;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
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
    public QueryResponseResult getAllUser(Integer page, Integer size);

    @ApiOperation("通过id查询用户")
    public User getUserById(@PathVariable("id") String id)  ;

    @ApiOperation("创建一条用户信息")
    public ResponseResult insertUser(@RequestBody @Valid User user);

    @ApiOperation("通过id删除用户")
    public ResponseResult deleteUserById(@PathVariable("user_id")String user_id) ;

    @ApiOperation("更新用户基本信息")
    public ResponseResult update(@RequestBody @Valid User user);

    @ApiOperation("更新用户手机号")
    public ResponseResult updateUserPhone(@RequestBody @Valid User user);

    @ApiOperation("根据user对象查询用户信息")
    public User getUser(@RequestBody @Valid User user);



}

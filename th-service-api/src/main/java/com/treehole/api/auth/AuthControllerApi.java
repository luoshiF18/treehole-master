package com.treehole.api.auth;

import com.treehole.framework.domain.member.request.LoginRequest;
import com.treehole.framework.domain.member.result.JwtResult;
import com.treehole.framework.domain.member.result.LoginResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Qbl
 * Created by 19:32 on 2019/11/12.
 * Version: 1.0
 */
@Api(value = "用户认证",description = "用户认证接口")
public interface AuthControllerApi {
    @ApiOperation("登录")
    public LoginResult login(LoginRequest loginRequest);

    @ApiOperation("退出")
    public ResponseResult logout();
    @ApiOperation("显示当前登录用户" )
    public JwtResult userjwt();
}

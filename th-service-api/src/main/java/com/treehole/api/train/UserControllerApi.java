package com.treehole.api.train;

import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.*;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

@Api(value="班级信息管理页面管理接口",description = "班级信息管理页面管理接口，提供页面的增、删、改、查")
public interface UserControllerApi {

    @ApiOperation("登录")
    public ResponseResult login(String userName,String userPassword, HttpServletRequest request);
    @ApiOperation("退出")
    public ResponseResult signOut(HttpServletRequest request);
    @ApiOperation("修改密码")
    public ResponseResult updatePassword(String userName,String oldPwd,String newPwd);
}

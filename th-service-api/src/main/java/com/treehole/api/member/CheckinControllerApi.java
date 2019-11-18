package com.treehole.api.member;

import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author shanhuijie
 * @Description: 签到api
 * @Date 2019.10.21 19:35
 */

@Api(value = "签到信息管理", description = "对签到信息进行增、删、查")
public interface CheckinControllerApi {
    @ApiOperation("查询所有签到信息")
    public Result findAllCheckin();

    @ApiOperation("插入一条签到信息")
    public Result insertCheckin(@RequestBody @Valid Role role) ;

    @ApiOperation("根据id删除签到信息")
    public Result deleteCheckinById(@PathVariable("role_id")String role_id) ;


}

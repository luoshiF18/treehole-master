package com.treehole.api.member;

import com.treehole.framework.domain.member.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shanhuijie
 * @Description:
 * @Date 2019.10.21 19:47
 */
@Api(value = "用户扩展信息管理", description = "对用户扩展信息进行查询")
public interface UserVoControllerApi {
    @ApiOperation("查询所有Vo用户信息")
    public Result findAllUserVo() throws Exception;

    @ApiOperation("根据用户uniq_ID查询用户Vo信息")
    public Result getUserVoByUniqId(@PathVariable String uniq_id) throws Exception;

    @ApiOperation("根据用户user_ID查询用户Vo信息")
    public Result getUserVoByUserId(@PathVariable String user_id) throws Exception;

    @ApiOperation("根据用户user_phone查询用户Vo信息")
    public Result getUserVoByUserPhone(@PathVariable String user_phone) throws Exception;
}
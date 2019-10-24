package com.treehole.api.member;

import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author shanhuijie
 * @Description: 积分controller  Api
 * @Date 2019.10.19 18:09
 */
@Api(value = "用户积分信息管理", description = "对用户积分信息进行增、删、查")
public interface PointControllerApi {
    @ApiOperation("查询所有积分")
    public QueryResponseResult findAllPoint(Integer page, Integer size);

    @ApiOperation("通过user_id查询积分")
    public QueryResponseResult getPointById(@PathVariable("user_id") String user_id,Integer page, Integer size);

    @ApiOperation("创建一条积分信息")
    public ResponseResult insertPoint(@RequestBody @Valid Points points) ;


    /*@ApiOperation("通过id删除积分")
    public ResponseResult deletePointById(@PathVariable("points_id") String points_id);*/

}

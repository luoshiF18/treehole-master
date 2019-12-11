package com.treehole.api.member;

import com.treehole.framework.domain.member.Checkin;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author shanhuijie
 * @Description: 签到api
 * @Date 2019.10.21 19:35
 */

@Api(value = "签到信息管理", description = "对签到信息进行增、删、查")
public interface CheckinControllerApi {
/*    @ApiOperation("查询所有签到信息")
    public QueryResponseResult findAllCheckin(Integer page,
                                              Integer size,
                                              String user_id);*/
/*
    @ApiOperation("根据user_id查询签到信息")
    public ResponseResult findCheckinByUserId(String user_id,Integer page, Integer size) ;*/

    @ApiOperation("插入一条签到信息")
    public ResponseResult insertCheckin(Checkin checkin) ;

    @ApiOperation("根据user_id删除签到信息")
    public ResponseResult deleteCheckinById(String user_id) ;
    @ApiOperation("根据check_id删除签到信息")
    public ResponseResult deleteByCheckId(String check_id);
    /*定时删除老旧数据*/

}

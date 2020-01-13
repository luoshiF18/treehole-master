package com.treehole.api.member;

import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author shanhuijie
 * @Description: 非付费会员API
 * @Date 2019.10.21 19:33
 */
@Api(value = "普通会员等级信息管理", description = "对普通会员等级信息进行增/删/改/查")
public interface FreeGradeControllerApi {
    @ApiOperation("根据rank,id,name查询所有用户等级信息")
    public QueryResponseResult findAllFreeGrade(Integer page, Integer size,
                                                GradeListRequest gradeListRequest);
    @ApiOperation("查询所有用户等级信息")
    public QueryResponseResult findAll();
    @ApiOperation("根据id查询会员等级信息")
    public FreeGrade findGradeById(String id);
    /*@ApiOperation("根据id查询普通会员等级信息")
    public FreeGrade findPayGradeById(String id) ;*/

    @ApiOperation("插入一条用户等级信息")
    public ResponseResult insertFreeGrade(FreeGrade freeGrade) ;

    @ApiOperation("根据id删除用户等级信息")
    public ResponseResult deleteFreeGrade(String id);

    @ApiOperation("更改用户等级信息")
    public ResponseResult update(FreeGrade freeGrade) ;

}

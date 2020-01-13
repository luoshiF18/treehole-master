package com.treehole.api.member;

import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author shanhuijie
 * @Description: 付费会员API
 * @Date 2019.10.21 19:33
 */
@Api(value = "付费会员等级信息管理", description = "对付费会员等级信息进行增/删/改/查")

public interface PayGradeControllerApi {

    @ApiOperation("根据rank,id,name查询所有付费会员等级信息")
    public QueryResponseResult findAllPayGrade(Integer page,
                                               Integer size,
                                               GradeListRequest gradeListRequest) ;
    @ApiOperation("查询所有VIP等级信息")
    public QueryResponseResult findAll();
    @ApiOperation("根据id查询会员等级信息")
    public PayGrade findGradeById(String id);
    /*@ApiOperation("根据id查询付费会员等级信息")
    public PayGrade findPayGradeById(String id) ;*/

    @ApiOperation("插入一条付费会员等级信息")
    public ResponseResult insertPayGrade(PayGrade payGrade) ;

    @ApiOperation("根据id删除付费会员等级信息")
    public ResponseResult deletePayGrade(String paygrade_id);

    @ApiOperation("修改付费会员等级信息")
    public ResponseResult update(PayGrade payGrade);
}

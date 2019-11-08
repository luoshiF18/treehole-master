package com.treehole.api.member;

import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author shanhuijie
 * @Description: 付费会员API
 * @Date 2019.10.21 19:33
 */
@Api(value = "付费会员等级信息管理", description = "对付费会员等级信息进行增/删/改/查")

public interface PayGradeControllerApi {

    @ApiOperation("查询所有付费会员等级信息")
    public QueryResponseResult findAllPayGrade(Integer page, Integer size) ;

    @ApiOperation("根据id查询付费会员等级信息")
    public PayGrade findPayGradeById(String id) ;

    @ApiOperation("插入一条付费会员等级信息")
    public ResponseResult insertPayGrade(PayGrade payGrade) ;

    @ApiOperation("根据id删除付费会员等级信息")
    public ResponseResult deletePayGrade(String paygrade_id);

    @ApiOperation("修改付费会员等级信息")
    public ResponseResult update(PayGrade payGrade);
}

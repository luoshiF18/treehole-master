package com.treehole.api.member;

import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.result.Result;
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
    public Result findAllPayGrade() throws Exception;

    @ApiOperation("插入一条付费会员等级信息")
    public Result insertPayGrade(@RequestBody @Valid PayGrade payGrade) throws Exception;

    @ApiOperation("根据id删除付费会员等级信息")
    public Result deletePayGrade(@PathVariable("card_id")String paygrade_id) throws Exception;

    @ApiOperation("更改付费会员等级信息")
    public Result update(@RequestBody @Valid PayGrade payGrade) throws Exception;
}

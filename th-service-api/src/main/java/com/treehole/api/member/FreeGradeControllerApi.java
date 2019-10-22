package com.treehole.api.member;

import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author shanhuijie
 * @Description: 非付费会员API
 * @Date 2019.10.21 19:33
 */
@Api(value = "非付费会员等级信息管理", description = "对非付费会员等级信息进行增/删/改/查")
public interface FreeGradeControllerApi {
    @ApiOperation("查询所有非付费会员等级信息")
    public Result findAllFreeGrade() throws Exception;

    @ApiOperation("插入一条非付费会员等级信息")
    public Result insertFreeGrade(@RequestBody @Valid FreeGrade freeGrade) throws Exception;

    @ApiOperation("根据id删除非付费会员等级信息")
    public Result deleteFreeGrade(@PathVariable("card_id")String freegrade_id) throws Exception;

    @ApiOperation("更改非付费会员等级信息")
    public Result update(@RequestBody @Valid FreeGrade freeGrade) throws Exception;

}

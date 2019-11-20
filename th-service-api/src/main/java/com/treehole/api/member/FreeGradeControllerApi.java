package com.treehole.api.member;

import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
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
@Api(value = "普通会员等级信息管理", description = "对普通会员等级信息进行增/删/改/查")
public interface FreeGradeControllerApi {
    @ApiOperation("根据rank,id,name查询所有普通会员等级信息")
    public QueryResponseResult findAllFreeGrade(Integer page, Integer size,
                                                GradeListRequest gradeListRequest);

    @ApiOperation("根据id查询普通会员等级信息")
    public FreeGrade findPayGradeById(String id) ;

    @ApiOperation("插入一条普通会员等级信息")
    public ResponseResult insertFreeGrade(FreeGrade freeGrade) ;

    @ApiOperation("根据id删除普通会员等级信息")
    public ResponseResult deleteFreeGrade(String id);

    @ApiOperation("更改普通会员等级信息")
    public ResponseResult update(FreeGrade freeGrade) ;

}

package com.treehole.api.member;

import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author shanhuijie
 * @Description: 会员卡api
 * @Date 2019.10.21 19:34
 */
@Api(value = "会员卡信息管理", description = "对会员卡信息进行增/删/改/查")
public interface CardsControllerApi {
    @ApiOperation("查询所有会员卡信息")
    public Result findAllCards() ;

    @ApiOperation("插入一条会员卡信息")
    public Result insertCard(@RequestBody @Valid Cards cards) ;

    @ApiOperation("根据id删除会员卡信息")
    public Result deleteCardById(@PathVariable("card_id")String card_id) ;

    @ApiOperation("更改会员卡信息")
    public Result update(@RequestBody @Valid Cards cards) ;

}

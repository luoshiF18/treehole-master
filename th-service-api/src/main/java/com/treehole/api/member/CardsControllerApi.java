package com.treehole.api.member;

import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.Role;
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
 * @Description: 会员卡api
 * @Date 2019.10.21 19:34
 */
@Api(value = "会员卡信息管理", description = "对会员卡信息进行增/删/改/查")
public interface CardsControllerApi {
    @ApiOperation("查询所有会员卡信息")
    public QueryResponseResult findAllCards(Integer page, Integer size) ;

    @ApiOperation("插入一条会员卡信息")
    public ResponseResult insertCard(@RequestBody @Valid Cards cards) ;

    @ApiOperation("根据user_id删除会员卡信息")
    public ResponseResult deleteCardById(@PathVariable("card_id")String card_id) ;

    @ApiOperation("更改会员卡信息")
    public ResponseResult update(@RequestBody @Valid Cards cards) ;

}

package com.treehole.api.member;

import com.treehole.framework.domain.member.Vo.CardsVo;
import com.treehole.framework.domain.member.resquest.CardListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author shanhuijie
 * @Description: 会员卡api
 * @Date 2019.10.21 19:34
 */
@Api(value = "会员卡信息管理", description = "对会员卡信息进行增/删/改/查")
public interface CardsControllerApi {
    @ApiOperation("查询所有会员卡信息(user_id,card_id,phone)")
    public QueryResponseResult findAllCards( Integer page,
                                             Integer size,
                                            CardListRequest cardListRequest) ;
    @ApiOperation("根据cardId查询cardVo对象")
    public CardsVo findCardById(String card_id);
    @ApiOperation("根据userId查询cardVo对象")
    public CardsVo findCardByUserId(String user_id);
    @ApiOperation("插入一条会员卡信息")
    public ResponseResult insertCard(String id);

    @ApiOperation("根据user_id删除会员卡信息")
    public ResponseResult deleteCardById(String user_id);

    @ApiOperation("更改会员卡信息")
    public ResponseResult update(CardsVo cardsvo) ;
   // public ResponseResult update(Cards cards) ;

 /*   @ApiOperation("根据user_id查找cards")
    public Cards findCardsByUserId(String user_id);*/

}

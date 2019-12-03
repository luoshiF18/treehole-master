package com.treehole.member.api;

import com.treehole.framework.domain.member.resquest.CardListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shanhuijie
 * @Description:暴露UserVo查询接口
 * @Date 2019.10.22 13:11
 */
public interface CardsApi {
    /*根据user_id,cards_id,user_phone,查询所有Vo用户信息*/
    @GetMapping("member/card/getAllCards/{page}/{size}")
    public QueryResponseResult findAllUserVo(@PathVariable("page") Integer page,
                                             @PathVariable("size") Integer size,
                                             CardListRequest cardListRequest);


}

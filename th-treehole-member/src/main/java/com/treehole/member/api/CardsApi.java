package com.treehole.member.api;

import com.treehole.framework.domain.member.resquest.CardListRequest;
import com.treehole.framework.domain.member.resquest.UserListRequest;
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
    @GetMapping("/card/getAllCards/{page}/{size}")
    public QueryResponseResult findAllUserVo(@PathVariable("page") Integer page,
                                             @PathVariable("size") Integer size,
                                             CardListRequest cardListRequest);

    /*
    根据id查找用户*//*
    @GetMapping("/uservo/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id);

    *//*根据user_phone查找用户*//*
    @GetMapping("/uservo/find/userPhone/{user_phone}")
    public UserVo getUserVoByUserPhone(@PathVariable("user_phone") String user_phone);

    @GetMapping("/uservo/find/nickname")
    public UserVo getUserVoByNickname(@RequestParam("nickname") String nickname);
    */
}

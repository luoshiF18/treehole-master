package com.treehole.member.api;

import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:暴露UserVo查询接口
 * @Date 2019.10.22 13:11
 */
public interface UserVoApi {
    /*根据user_id,user_nickname,user_phone,查询所有Vo用户信息*/
    @GetMapping("member/user/getAllUserVos/{page}/{size}")
    public QueryResponseResult findAllUserVo(@PathVariable("page") Integer page,
                                             @PathVariable("size") Integer size,
                                             UserListRequest userListRequest);

    @GetMapping("member/user/getUserByNicknames")
    public QueryResponseResult findUserByNicknames(List<String> names);

    //根据id查找用户
    @GetMapping("member/user/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id);

    /**//*根据user_phone查找用户*//*
    @GetMapping("/uservo/find/userPhone/{user_phone}")
    public UserVo getUserVoByUserPhone(@PathVariable("user_phone") String user_phone);*/

    @GetMapping("member/user/getUserByNickname")
    public UserVo getUserVoByNickname(@RequestParam("nickname") String nickname);

    @GetMapping("member/user/getUserByTime")
    public QueryResult findUserByTime(@RequestParam("beforeTime") Date beforeTime,
                                      @RequestParam("afterTime") Date afterTime);

}

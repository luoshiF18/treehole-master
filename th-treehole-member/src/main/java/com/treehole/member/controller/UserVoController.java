package com.treehole.member.controller;

import com.treehole.api.member.UserVoControllerApi;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.member.service.UserService;
import com.treehole.member.service.UserVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shanhuijie
 * @Description:
 * @Date 2019.10.21 19:46
 */
@RestController
@RequestMapping("uservo")
public class UserVoController implements UserVoControllerApi {
    @Autowired
    private UserService userService;

    @Autowired
    UserVoService userVoService;

    @GetMapping("/getAllUserVos/{page}/{size}")
    public QueryResponseResult findAllUserVo(@PathVariable("page") Integer page,
                                             @PathVariable("size") Integer size,
                                             UserListRequest userListRequest){

        return userVoService.findAllUserVos(page,size,userListRequest);
    }

   /* @GetMapping("/getAllUserVos")
    public QueryResponseResult getAllUserVo(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "5") Integer size){
        QueryResult queryResult = userVoService.findAllUserVos(page, size);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }*/

   /* @GetMapping("/find/uniqId/{uniq_id}")
    public UserVo getUserVoByUniqId(@PathVariable("uniq_id") String uniq_id)  {
        //System.out.println("==========+++++++++11111   "+id);
         return userVoService.getUserByUniqId(uniq_id);

    }*/

    @GetMapping("/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id)  {
        //System.out.println("==========+++++++++11111   "+id);
         return userVoService.getUserByUserId(user_id);
        //System.out.println("==========+++++++++      "+res);

    }

    @GetMapping("/find/userPhone/{user_phone}")
    public UserVo getUserVoByUserPhone(@PathVariable("user_phone") String user_phone)  {
        //System.out.println("==========+++++++++11111   "+user_phone);
        return userVoService.getUserByUserPhone(user_phone);

    }

    @GetMapping("/find/nickname")
    public UserVo getUserVoByNickname(@RequestParam("nickname") String nickname)  {
        //System.out.println("==========+++++++++11111   "+user_phone);
        return userVoService.getUserByNickname(nickname);

    }

}

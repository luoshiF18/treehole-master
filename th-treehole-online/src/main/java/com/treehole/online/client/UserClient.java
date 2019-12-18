package com.treehole.online.client;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Qbl
 * Created by 19:30 on 2019/11/12.
 * Version: 1.0
 */
@FeignClient(value = "th-treehole-member")
public interface UserClient {
   /* @GetMapping("/user/getUserExt")
    public UserExt getUserExt(@RequestParam("userNickName") String userNickName);*/

    @PostMapping("/member/user/insert")
    public ResponseResult insertUser(@RequestBody @Valid User user);

    @GetMapping("/member/user/getUserByNickname")
    public UserVo getUserVoByNickname(@RequestParam(value = "nickname") String nickname);

    @DeleteMapping(value ="/member/user/delete/{user_id}")
    public ResponseResult deleteUserById(@PathVariable("user_id") String user_id);
}

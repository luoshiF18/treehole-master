package com.treehole.evaluation.client;

import com.treehole.framework.domain.member.Vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 15:32 on 2019/10/24.
 * Version: 1.0
 */
@FeignClient(value = "th-treehole-member")
@Repository
public interface UserClient {

    @GetMapping("member/user/getUserByNickname")
    public UserVo getUserVoByNickname(@RequestParam(value ="nickname") String nickname);

    @GetMapping("member/user/get/warningUser")
    public List<UserVo> getAllUser(@RequestParam("listUserId") List listUserId);

    @GetMapping("member/user/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id);

    @GetMapping("member/user/getUserByNicknames")
    public List<UserVo> findUserByNicknames(@RequestParam(value ="names") List<String> names);
}

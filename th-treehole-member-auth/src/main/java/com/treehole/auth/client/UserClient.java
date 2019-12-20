package com.treehole.auth.client;

import com.treehole.framework.domain.member.ext.UserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Qbl
 * Created by 19:30 on 2019/11/12.
 * Version: 1.0
 */
@FeignClient(value = "th-treehole-member")
public interface UserClient {
    @GetMapping("member/user/getUserExt")
    public UserExt getUserExt(@RequestParam("userNickName") String userNickName);

}

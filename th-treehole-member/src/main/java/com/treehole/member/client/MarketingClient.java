package com.treehole.member.client;

import com.treehole.framework.domain.member.ext.UserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: shj
 * Created by 19:30 on 2019/12/3.
 * Version: 1.0
 */
@FeignClient(value = "th-treehole-marketing")
public interface MarketingClient {
    @GetMapping("member/user/getUserExt")
    public UserExt getUserExt(@RequestParam("userNickName") String userNickName);

}

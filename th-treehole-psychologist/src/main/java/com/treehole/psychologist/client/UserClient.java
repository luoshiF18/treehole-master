package com.treehole.psychologist.client;

import com.treehole.framework.domain.member.Vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Helay
 * @date 2019/11/26 10:25
 */
@FeignClient(value = "TH-TREEHOLE-MEMBER")
public interface UserClient {

    /**
     * 通过id查询用户
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("/member/user/find/userId/{id}")
    UserVo getUserVoByUserId(@PathVariable("id") String id);
}

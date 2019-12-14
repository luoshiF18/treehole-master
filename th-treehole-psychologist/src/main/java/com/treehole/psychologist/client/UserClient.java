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

    @GetMapping("member/user/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id);

}

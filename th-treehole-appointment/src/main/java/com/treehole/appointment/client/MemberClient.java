package com.treehole.appointment.client;

import com.treehole.framework.domain.member.Vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName MemberClient
 * @Description: 调用用户接口
 * @Author XDD
 * @Date 2019/11/30 20:23
 **/
@FeignClient(value = "th-treehole-member")  //要调用哪个服务 从eruka列表中找服务名
public interface MemberClient {
    @GetMapping("member/user/find/userId/{user_id}")   //用getmapping标识远程调用的http的方法类型
    UserVo getUserVoByUserId(@PathVariable("user_id") String user_id);


}

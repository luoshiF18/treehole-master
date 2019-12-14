package com.treehole.member.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: shj
 * Created by 19:30 on 2019/12/3.
 * Version: 1.0
 */
@FeignClient(value = "th-treehole-marketing")  //要调用哪个服务 从eruka列表中找服务名
public interface MemberClient {
/*    @GetMapping("/marketing/activity/all/{id}")  //用getmapping标识远程调用的http的方法类型
    public ActivityRequest queryActivityAllById(@PathVariable("id") String id);*/

}

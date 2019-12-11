package com.treehole.member.client;

import com.treehole.framework.domain.marketing.request.ActivityRequest;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.marketing.api.ActivitySelectApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: shj
 * Created by 19:30 on 2019/12/3.
 * Version: 1.0
 */
@FeignClient(value = "th-treehole-marketing")  //要调用哪个服务 从eruka列表中找服务名
public interface MemberClient extends ActivitySelectApi {
    @GetMapping("/marketing/activity/all/{id}")  //用getmapping标识远程调用的http的方法类型
    public ActivityRequest queryActivityAllById(@PathVariable("id") String id);

}

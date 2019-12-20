package com.treehole.archives.client;

import com.treehole.framework.client.ThServiceList;
import com.treehole.member.api.UserVoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 13:31
 * @description: 用户调用相关接口
 */
@FeignClient(value = ThServiceList.TH_TREEHOLE_MEMBER)
public interface UserVoClient extends UserVoApi {
}

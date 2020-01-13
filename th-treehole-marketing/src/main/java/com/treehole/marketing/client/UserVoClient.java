package com.treehole.marketing.client;

import com.treehole.framework.client.ThServiceList;
import com.treehole.member.api.UserVoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wanglu
 */
@FeignClient(value = ThServiceList.TH_TREEHOLE_MEMBER)
public interface UserVoClient extends UserVoApi {
}

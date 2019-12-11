package com.treehole.marketing.client;

import com.treehole.evaluation.api.ScaleSelectApi;
import com.treehole.framework.client.ThServiceList;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author

 * @description: 远程调用测评服务中测评结果接口
 */
@FeignClient(value = ThServiceList.TH_TREEHOLE_EVALUATION)
public interface ScaleSelectClient extends ScaleSelectApi {
}


package com.treehole.marketing.client;

import com.treehole.evaluation.api.ScaleSelectApi;
import com.treehole.framework.client.ThServiceList;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 8:49
 * @description: 远程调用测评服务中测评结果接口
 */
@FeignClient(value = ThServiceList.TH_TREEHOLE_EVALUATION)
public interface ScaleSelectClient extends ScaleSelectApi {
}


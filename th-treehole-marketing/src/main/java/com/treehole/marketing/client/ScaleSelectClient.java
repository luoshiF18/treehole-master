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
   //@GetMapping("scale/type/all")
   //public QueryResponseResult findScaleType();

   //@GetMapping("scale/type")
   //public QueryResponseResult findScaleByType(
   //        @RequestParam(value = "page", defaultValue = "1") Integer page,
   //        @RequestParam(value = "size", defaultValue = "5") Integer size,
   //        @RequestParam(value = "scaleTypeId") String scaleTypeId,
   //        @RequestParam(value = "isFree", defaultValue = "true") Boolean isFree);
}


package com.treehole.marketing.client;

<<<<<<< HEAD
import com.treehole.api.evaluation.ScaleSelectControllerApi;
import com.treehole.evaluation.api.ScaleSelectApi;
import com.treehole.framework.client.ThServiceList;
import com.treehole.framework.model.response.QueryResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
=======
import com.treehole.evaluation.api.ScaleSelectApi;
import com.treehole.framework.client.ThServiceList;
import org.springframework.cloud.openfeign.FeignClient;
>>>>>>> master

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


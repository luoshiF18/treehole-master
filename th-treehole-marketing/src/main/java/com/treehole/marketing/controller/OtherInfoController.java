package com.treehole.marketing.controller;

import com.treehole.api.marketing.OtherInfoControllerApi;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.marketing.client.ScaleSelectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/other")
public class OtherInfoController implements OtherInfoControllerApi {

    @Autowired
    private ScaleSelectClient scaleSelectClient;


    /**
     * 获取所有分类
     *
     * @return
     */
    @GetMapping("scale/type/all")
    public QueryResponseResult findScaleType() {
        return this.scaleSelectClient.findScaleType();
    }

    /**
     * 根据分类id查询量表
     *
     * @param page
     * @param size
     * @param scaleTypeId
     * @return
     */
    @GetMapping("scale/type")
    public QueryResponseResult findScaleByType(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "scaleTypeId") String scaleTypeId,
            @RequestParam(value = "isFree", defaultValue = "true") Boolean isFree) {
        return this.scaleSelectClient.findScaleByType(page, size, scaleTypeId, false);
       // return null;
    }


}

package com.treehole.marketing.controller;

import com.netflix.discovery.converters.Auto;
import com.treehole.api.marketing.OtherInfoControllerApi;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.client.ScaleSelectClient;
import com.treehole.marketing.client.UserVoClient;
import com.treehole.marketing.service.OtherInfoService;
import lombok.experimental.var;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/other")
public class OtherInfoController implements OtherInfoControllerApi {

    @Autowired
    private ScaleSelectClient scaleSelectClient;

    @Autowired
    private UserVoClient userVoClient;
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


    /**
     * 根据起止时间查询注册会员
     * @param beforeTime
     * @param afterTime
     * @return
     */
    @GetMapping("member/user/getUserByTime")
    public QueryResult findUserByTime(@RequestParam("beforeTime") String beforeTime,
                                      @RequestParam("afterTime") String afterTime)  {

        System.out.println(beforeTime);
        System.out.println(afterTime);

        return this.userVoClient.findUserByTime(beforeTime,afterTime);
    }


}

package com.treehole.appointment.client;

import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.domain.psychologist.ext.DetailExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName PsychologistClient
 * @Description: 调用咨询师接口
 * @Author XDD
 * @Date 2019/11/30 19:23
 **/
@FeignClient(value = "th-treehole-psychologist") //指定远程调用的服务名
public interface PsychologistClient {
    //根据咨询师id查询更多信息，门户展示使用
    @GetMapping("/psychologist/profile/get/detail/{id}")
    DetailExt getPsychologistDetail(@PathVariable("id") String id);
    //根据咨询师id查询咨询师详情信息
    @GetMapping("/psychologist/detail/get/{psychologist_id}")
    Detail getDetailById(@PathVariable("psychologist_id") String psychologist_id);
}

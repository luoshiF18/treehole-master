package com.treehole.appointment.client;

import com.treehole.framework.domain.psychologist.Detail;
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
    //根据咨询师id查询咨询师信息，远程调用psychologist请求数据
    @GetMapping("/psychologist/detail/get/{psychologist_id}") //用GetMapping标识远程调用的http的方法类型
    Detail getDetailById(@PathVariable("psychologist_id") String id);
}

package com.treehole.psychologist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@EnableFeignClients //开启feignClient
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.treehole.framework.domain.psychologist") // 扫描实体类
@ComponentScan(basePackages = {"com.treehole.api"}) // 扫描接口
@ComponentScan(basePackages = {"com.treehole.framework"}) // 扫描common
@ComponentScan(basePackages = {"com.treehole.psychologist"}) //扫描本项目下的所有类,也可以不写
@MapperScan("com.treehole.psychologist.dao")
public class PsychologistApplication {
    public static void main(String[] args) {
        SpringApplication.run(PsychologistApplication.class, args);
    }
}

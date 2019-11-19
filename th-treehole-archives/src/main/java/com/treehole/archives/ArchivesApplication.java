package com.treehole.archives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 15:05
 * @description: 心理测评档案
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients  //启动feign
//@EntityScan(value = {"com.treehole.framework.domain.learning"})//扫描实体类
@ComponentScan(basePackages={"com.treehole.api"})//扫描接口
@ComponentScan(basePackages={"com.treehole.archives"})//扫描接口
@ComponentScan(basePackages={"com.treehole.framework"})//扫描common下的所有类
public class ArchivesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArchivesApplication.class,args);
    }
}

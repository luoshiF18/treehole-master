package com.treehole.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 16:12
 * @description:
 */
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.treehole.framework.domain.onlineCourse")//扫描实体类
@ComponentScan(basePackages={"com.treehole.api"})//扫描接口
@ComponentScan(basePackages={"com.treehole.search"})//扫描本项目下的所有类
@ComponentScan(basePackages={"com.treehole.framework"})//扫描common下的所有类
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }
}

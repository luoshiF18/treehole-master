package com.treehole.marketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wanglu
 * @description: 营销活动
 */
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients  //启动feign
@SpringBootApplication
@EntityScan("com.treehole.framework.domain.marketing")//扫描实体类
@MapperScan("com.treehole.marketing.dao")
@ComponentScan(basePackages = {"com.treehole.api"})//扫描接口
@ComponentScan(basePackages = {"com.treehole.framework"})//扫描common
public class MarketingApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketingApplication.class, args);
    }
}

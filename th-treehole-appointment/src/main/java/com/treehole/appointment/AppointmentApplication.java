package com.treehole.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName AppointmentApplication
 * @Description: TODO
 * @Author XDD
 * @Date 2019/10/25
 * @Version V1.0
 **/
@SpringBootApplication
@EntityScan("com.treehole.framework.domain.appointment") //扫描实体类
@ComponentScan(basePackages = {"com.treehole.api"}) //扫描接口
@ComponentScan(basePackages = {"com.treehole.framework"}) //扫描common
@ComponentScan(basePackages = {"com.treehole.appointment"}) //扫描本项目下的所有类
@EnableDiscoveryClient
@EnableFeignClients
public class AppointmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppointmentApplication.class,args);
    }
}

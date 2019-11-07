package com.treehole.train;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients  //进行远程调用
@EnableDiscoveryClient //表示它是一个Eureka的客户端
@SpringBootApplication
@EntityScan("com.treehole.framework.domain.train")//扫描实体类
@ComponentScan(basePackages={"com.treehole.api"})//扫描接口
@ComponentScan(basePackages={"com.treehole.train"})//扫描本类
@ComponentScan(basePackages={"com.treehole"})
public class TrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainApplication.class,args);
    }

}
